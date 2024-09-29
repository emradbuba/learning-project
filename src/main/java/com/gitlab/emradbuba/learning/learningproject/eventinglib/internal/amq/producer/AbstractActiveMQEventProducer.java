package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.producer;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.LoggingEventUtils;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.message.ActiveMQMessageConverter;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.model.LearningAppMessage;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.lifecycle.EventingLifecycleEntity;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.settings.EventProducerSettingsCore;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.EventProducer;
import jakarta.jms.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.artemis.jms.client.*;

@Slf4j
public abstract class AbstractActiveMQEventProducer implements EventProducer, EventingLifecycleEntity {

    protected final EventProducerSettingsCore eventProducerSettingsCore;
    protected final String producerName;

    protected final String applicationName;
    protected ActiveMQConnection connection = null;
    protected ActiveMQSession session = null;
    protected ActiveMQMessageProducer producer = null;

    protected AbstractActiveMQEventProducer(EventProducerSettingsCore eventProducerSettingsCore) {
        this.producerName = eventProducerSettingsCore.getProducerName();
        this.eventProducerSettingsCore = eventProducerSettingsCore;
        this.applicationName = eventProducerSettingsCore.getMicroServiceName();
    }

    private void startProducer() throws JMSException {

        log.info("EventProducer '{}': Initializing phase...", producerName);
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                eventProducerSettingsCore.getBrokerUrl(),
                eventProducerSettingsCore.getBrokerUsername(),
                eventProducerSettingsCore.getBrokerPassword()
        );

        createConnection(createUniqueConnectionClientID(), connectionFactory);
        createSession();
        createMessageProducer();
        startConnection();
        log.info("PRODUCER STARTED [Client=<{}> | Service=<{}> | Producer <{}>] STARTED SUCCESSFULLY :-)",
                connection.getClientID(),
                applicationName,
                producerName
        );
    }

    private void createConnection(String clientId, ActiveMQConnectionFactory connectionFactory) throws JMSException {
        log.info("EventProducer '{}': Creating connection... | Setting clientID='{}'...", producerName, clientId);
        connection = (ActiveMQConnection) connectionFactory.createConnection();
        connection.setClientID(clientId);
    }

    private String createUniqueConnectionClientID() {
        return applicationName + "::" + producerName;
    }

    private void createSession() throws JMSException {
        boolean transacted = false;
        int autoAcknowledge = Session.AUTO_ACKNOWLEDGE;
        log.info("EventProducer '{}': Creating session... | Transacted={}, AckMode={}", producerName, transacted, autoAcknowledge);
        session = (ActiveMQSession) connection.createSession(transacted, autoAcknowledge);
    }

    protected abstract void createMessageProducer() throws JMSException;

    @Override
    public void sendMessage(LearningAppMessage learningAppMessage) {

        try {
            ActiveMQTextMessage activeMQTextMessage = ActiveMQMessageConverter.toActiveMQMessage(learningAppMessage, session);

            producer.send(activeMQTextMessage);

            LoggingEventUtils.logOutcomingEvent(this, learningAppMessage); // workaround - normally logging would be AMQ-specific

        } catch (JMSException e) {
            log.error(String.format("[EventProducer '%s']: Could not send message %s", producerName, learningAppMessage.getMessageId()), e);
        }
    }

    private void startConnection() throws JMSException {
        log.info("EventProducer '{}': Starting <{}> connection...", producerName, connection.getClientID());
        connection.start();
    }

    @Override
    public void startEventingLifecycleEntity() {
        try {
            log.info("EventProducer '{}': Starting...", producerName);
            startProducer();
        } catch (JMSException e) {
            log.error(String.format("EventProducer '%s': Error while starting producer - verify the environment...", producerName), e);
        }
    }

    @Override
    public void stopEventingLifecycleEntity() {
        try {
            if (producer != null) {
                producer.close();
                log.info("EventProducer '{}': Closing producer...", producerName);
            }
            if (session != null) {
                session.close();
                log.info("EventProducer '{}': Closing session...", producerName);
            }
            if (connection != null) {
                log.info("EventProducer '{}': Closing connection {}...", producerName, connection.getClientID());
                connection.close();
            }
            log.info("EventProducer '{}': Cleanup successfully finished...", producerName);
        } catch (JMSException e) {
            log.error("EventProducer '{}': Cleanup finished with errors - verify the environment...", producerName);
        }
    }

    @Override
    public String getName() {
        return producerName;
    }

    @Override
    public String getApplicationName() {
        return applicationName;
    }
}
