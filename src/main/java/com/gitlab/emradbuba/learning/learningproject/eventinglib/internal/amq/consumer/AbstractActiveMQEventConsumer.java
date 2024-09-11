package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.consumer;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.message.ActiveMQMessageConverter;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.lifecycle.EventingLifecycleEntity;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.settings.EventConsumerSettingsCore;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.EventConsumer;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.model.LearningAppAmqMessage;
import jakarta.jms.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQTextMessage;

@Slf4j
public abstract class AbstractActiveMQEventConsumer implements EventConsumer, EventingLifecycleEntity {

    protected final EventConsumerSettingsCore eventConsumerSettingsCore;
    protected final String consumerName;

    protected final String uniqueMicroServiceName;
    protected Connection connection = null;
    protected Session session = null;
    protected MessageConsumer consumer = null;

    protected AbstractActiveMQEventConsumer(final EventConsumerSettingsCore eventConsumerSettingsCore) {
        this.eventConsumerSettingsCore = eventConsumerSettingsCore;
        this.consumerName = eventConsumerSettingsCore.getConsumerName();
        this.uniqueMicroServiceName = eventConsumerSettingsCore.getMicroServiceName();
    }

    private void startListening() throws JMSException {

        log.info("EventConsumer '{}': Initializing phase...", consumerName);
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

        log.info("[Client=<{}> | Service=<{}> | Consumer <{}>] STARTED SUCCESSFULLY :-)", connection.getClientID(), uniqueMicroServiceName, consumerName);
    }

    private void createConnection(String clientId, ActiveMQConnectionFactory connectionFactory) throws JMSException {
        log.info("EventConsumer '{}': Creating connection... | Setting clientID='{}'...", consumerName, clientId);
        connection = connectionFactory.createConnection();
        connection.setClientID(clientId);
    }

    private String createUniqueConnectionClientID() {
        return uniqueMicroServiceName + "_" + consumerName;
    }

    private void createSession() throws JMSException {
        boolean transacted = false;
        int autoAcknowledge = Session.AUTO_ACKNOWLEDGE;
        log.info("EventConsumer '{}': Creating session... | Transacted={}, AckMode={}", consumerName, transacted, autoAcknowledge);
        session = connection.createSession(transacted, autoAcknowledge);
    }

    protected abstract void createMessageConsumer() throws JMSException;

    private void addErrorListener() throws JMSException {
        connection.setExceptionListener(exception -> {
            // TODO: listener should be separate
            log.error("MessageConsumer '{}' could not handle an incoming message: <{}>", consumerName, exception.getMessage());
        });
    }

    private void addMessageListener() throws JMSException {
        log.info("EventConsumer '{}': Adding message and error listeners...", consumerName);
        consumer.setMessageListener(message -> {
            // TODO: listener should be separate
            if (message instanceof ActiveMQTextMessage activeMQTextMessage) {
                try {
                    LearningAppAmqMessage learningAppAmqMessage = ActiveMQMessageConverter.fromActiveMQTextMessage(activeMQTextMessage);
                    log.info("ReceivedMessage! {}->{} | {} | {} '{}' (trigger: {})",
                            connection.getClientID(),
                            uniqueMicroServiceName,
                            consumerName,
                            learningAppAmqMessage.getMessageId(),
                            learningAppAmqMessage.getMessageContent(),
                            learningAppAmqMessage.getMessageTrigger()
                    );
                } catch (JMSException e) {
                    log.error("Could not read message...");
                }
            }
        });
    }

    private void startConnection() throws JMSException {
        log.info("EventConsumer '{}': Starting <{}> connection...", consumerName, connection.getClientID());
        connection.start();
    }

    @Override
    public void startEventingLifecycleEntity() {
        try {
            log.info("EventConsumer '{}': Starting...", consumerName);
            startListening();
        } catch (JMSException e) {
            log.error(String.format("EventConsumer '%s': Error while starting consumer - verify the environment...", consumerName), e);
        }
    }

    @Override
    public void stopEventingLifecycleEntity() {
        try {
            if (consumer != null) {
                consumer.close();
                log.info("EventConsumer '{}': Closing consumer...", consumerName);
            }
            // TODO: perform some kind of durable UNsubscription if necessary (Topic and vTopic cases...)
            if (session != null) {
                session.close();
                log.info("EventConsumer '{}': Closing session...", consumerName);
            }
            if (connection != null) {
                log.info("EventConsumer '{}': Closing connection {}...", consumerName, connection.getClientID());
                connection.close();
            }
            log.info("EventConsumer '{}': Cleanup successfully finished...", consumerName);
        } catch (JMSException e) {
            log.error("EventConsumer '{}': Cleanup finished with errors - verify the environment...", consumerName);
        }
    }
}
