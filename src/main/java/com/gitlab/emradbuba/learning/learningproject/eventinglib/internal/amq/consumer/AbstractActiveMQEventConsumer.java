package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.consumer;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.lifecycle.EventingLifecycleEntity;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.settings.EventConsumerSettingsCore;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.EventConsumer;
import jakarta.annotation.PreDestroy;
import jakarta.jms.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.artemis.jms.client.*;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

@Slf4j
public abstract class AbstractActiveMQEventConsumer implements EventConsumer, EventingLifecycleEntity {

    private static final String CLIENT_ID_PREFIX = "Client_";
    protected final EventConsumerSettingsCore eventConsumerSettingsCore;
    protected final String uniqueConsumerName;
    protected final String microServiceName;

    protected Connection connection = null;
    protected Session session = null;
    protected MessageConsumer consumer = null;

    protected AbstractActiveMQEventConsumer(final EventConsumerSettingsCore eventConsumerSettingsCore) {
        this.eventConsumerSettingsCore = eventConsumerSettingsCore;
        this.uniqueConsumerName = eventConsumerSettingsCore.getUniqueConsumerName();
        this.microServiceName = eventConsumerSettingsCore.getMicroServiceName();
    }

    private void startListening() throws JMSException {

        log.info("EventConsumer '{}': Initializing phase...", uniqueConsumerName);
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                eventConsumerSettingsCore.getBrokerUrl(),
                eventConsumerSettingsCore.getBrokerUsername(),
                eventConsumerSettingsCore.getBrokerPassword()
        );

        final String clientID = CLIENT_ID_PREFIX + microServiceName + "_" + uniqueConsumerName;

        log.info("EventConsumer '{}': Creating connection | ClientID='{}'...", uniqueConsumerName, clientID);
        connection = connectionFactory.createConnection();
        connection.setClientID(clientID);

        log.info("EventConsumer '{}': Creating session...", uniqueConsumerName);
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        consumer = createMessageConsumer();
        log.info("EventConsumer '{}': Adding message and error listeners...", uniqueConsumerName);
        consumer.setMessageListener(message -> {
            // TODO: listener should be separate
            if (message instanceof TextMessage) {
                try {
                    TextMessage textMessage = (TextMessage) message;
                    String s = textMessage.getText();
                    log.info("[Consumer <{}>] Consuming message: '{}'", uniqueConsumerName, s);
                    textMessage.acknowledge();
                } catch (JMSException e) {
                    log.error("Could not read message...");
                }
            }
        });
        connection.setExceptionListener(exception -> {
            // TODO: listener should be separate
            log.error("MessageConsumer '{}' could not handle an incoming message: <{}>", uniqueConsumerName, exception.getMessage());
        });

        log.info("EventConsumer '{}': Starting connection ...", uniqueConsumerName);
        connection.start();
        log.info("EventConsumer '{}': STARTED SUCCESSFULLY...", uniqueConsumerName);
    }

    protected abstract MessageConsumer createMessageConsumer() throws JMSException;

    @EventListener(ApplicationStartedEvent.class)
    @Override
    public void startEventingLifecycleEntity() {
        try {
            log.info("EventConsumer '{}': Starting...", uniqueConsumerName);
            startListening();
        } catch (JMSException e) {
            // TODO: Handle this case...
            log.error("EventConsumer '{}': Error while starting consumer - verify the environment...", uniqueConsumerName);
        }
    }

    @PreDestroy
    @Override
    public void stopEventingLifecycleEntity() {
        try {
            if (consumer != null) {
                consumer.close();
                log.info("EventConsumer '{}': Closing consumer...", uniqueConsumerName);
            }
            // TODO: perform some kind of durable UNsubscription if necessary (Topic and vTopic cases...)
            if (session != null) {
                session.close();
                log.info("EventConsumer '{}': Closing session...", uniqueConsumerName);
            }
            if (connection != null) {
                log.info("EventConsumer '{}': Closing connection (clientID={})...", uniqueConsumerName, connection.getClientID());
                connection.close();
            }
            log.info("EventConsumer '{}': Cleanup successfully finished...", uniqueConsumerName);
        } catch (JMSException e) {
            log.error("EventConsumer '{}': Cleanup finished with errors - verify the environment...", uniqueConsumerName);
        }
    }
}
