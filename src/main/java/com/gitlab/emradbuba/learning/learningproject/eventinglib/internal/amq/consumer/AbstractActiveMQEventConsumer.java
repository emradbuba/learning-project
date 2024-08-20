package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.consumer;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.lifecycle.EventingLifecycleEntity;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.settings.EventConsumerSettingsCore;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.EventConsumer;
import jakarta.annotation.PreDestroy;
import jakarta.jms.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

import java.util.UUID;

import static com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.EventingUtils.CONNECTION_CLIENT_ID_PREFIX;

@Slf4j
public abstract class AbstractActiveMQEventConsumer implements EventConsumer, EventingLifecycleEntity {

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

        createConnection(createUniqueConnectionClientID(), connectionFactory);
        createSession();
        createMessageConsumer();
        addMessageListener();
        addErrorListener();
        startConnection();

        log.info("EventConsumer '{}': STARTED SUCCESSFULLY with clientId '{}'...", uniqueConsumerName, connection.getClientID());
    }

    private void createConnection(String clientId, ActiveMQConnectionFactory connectionFactory) throws JMSException {
        log.info("EventConsumer '{}': Creating connection... | Setting clientID='{}'...", uniqueConsumerName, clientId);
        connection = connectionFactory.createConnection();
        connection.setClientID(clientId);
    }

    private String createUniqueConnectionClientID() {
        final String randomSuffix = UUID.randomUUID().toString().substring(0, 8);
        final String prefixedServiceName = CONNECTION_CLIENT_ID_PREFIX + microServiceName.toUpperCase();

        return prefixedServiceName + "_" + randomSuffix;
    }

    private void createSession() throws JMSException {
        boolean transacted = false;
        int autoAcknowledge = Session.AUTO_ACKNOWLEDGE;
        log.info("EventConsumer '{}': Creating session... | Transacted={}, AckMode={}", uniqueConsumerName, transacted, autoAcknowledge);
        session = connection.createSession(transacted, autoAcknowledge);
    }

    public abstract void createMessageConsumer() throws JMSException;

    private void addErrorListener() throws JMSException {
        connection.setExceptionListener(exception -> {
            // TODO: listener should be separate
            log.error("MessageConsumer '{}' could not handle an incoming message: <{}>", uniqueConsumerName, exception.getMessage());
        });
    }

    private void addMessageListener() throws JMSException {
        log.info("EventConsumer '{}': Adding message and error listeners...", uniqueConsumerName);
        consumer.setMessageListener(message -> {
            // TODO: listener should be separate
            if (message instanceof TextMessage) {
                try {
                    TextMessage textMessage = (TextMessage) message;
                    String s = textMessage.getText();
                    log.info("[Consumer <{}> | Client={}] Consuming message: '{}'", uniqueConsumerName, connection.getClientID(), s);
                } catch (JMSException e) {
                    log.error("Could not read message...");
                    // TODO: add some exception to retry the event or put it on DLQ
                }
            }
        });
    }

    private void startConnection() throws JMSException {
        log.info("EventConsumer '{}': Starting connection ...", uniqueConsumerName);
        connection.start();
    }

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
