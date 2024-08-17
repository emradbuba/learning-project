package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.producer;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.lifecycle.EventingLifecycleEntity;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.settings.EventProducerSettingsCore;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.EventProducer;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventCommunicationModelType;
import jakarta.jms.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

@Slf4j
public class ActiveMQEventProducer implements EventProducer, EventingLifecycleEntity {

    public static final String AMQ_VIRTUAL_TOPIC_PREFIX = "VirtualTopic.";
    private final EventProducerSettingsCore eventProducerSettingsCore;
    private final String producerName;

    private Connection connection = null;
    private Session session = null;
    private MessageProducer producer = null;
    private boolean isRunning = false;

    public ActiveMQEventProducer(EventProducerSettingsCore eventProducerSettingsCore) {
        this.producerName = eventProducerSettingsCore.getProducerName();
        this.eventProducerSettingsCore = eventProducerSettingsCore;
    }

    @Override
    public void startEventingLifecycleEntity() {
        try {
            if (!isRunning) {
                startProducer();
            }
        } catch (JMSException e) {
            log.error(String.format("Cannot start event producer '%s'", producerName), e);
        }
    }

    private void startProducer() throws JMSException {
        log.info("EventProducer '{}': Starting...", producerName);
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                eventProducerSettingsCore.getBrokerUrl(),
                eventProducerSettingsCore.getBrokerUsername(),
                eventProducerSettingsCore.getBrokerPassword()
        );

        // Get a connection from connectionFactory and start it....
        log.info("EventProducer '{}': Creating connection...", producerName);
        connection = connectionFactory.createConnection();
        connection.start();

        // Create a session having a connection...
        log.info("EventProducer '{}': Creating session...", producerName);
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create a Queue or Topic (will be created automatically by ActiveMQ if it doesn't exist)
        Destination producerDestination = createProducerDestination();

        // Create a MessageConsumer from the Session to the Destination
        producer = session.createProducer(producerDestination);
        log.info("Successfully created AMQ message producer '{}'", producerName);
        isRunning = true;
    }

    private Destination createProducerDestination() throws JMSException {
        String destinationName = eventProducerSettingsCore.getDestinationName();
        if (eventProducerSettingsCore.getEventCommunicationModelType() == EventCommunicationModelType.AMQ_PEER_TO_PEER) {
            log.info("EventProducer '{}': Creating queue '{}'...", producerName, destinationName);
            return session.createQueue(destinationName);
        }
        if (eventProducerSettingsCore.getEventCommunicationModelType() == EventCommunicationModelType.AMQ_PUBLISH_SUBSCRIBE) {
            log.info("EventProducer '{}': Creating topic '{}'...", producerName, destinationName);
            return session.createTopic(destinationName);
        }

        String virtualTopicName = AMQ_VIRTUAL_TOPIC_PREFIX + destinationName;
        log.info("EventProducer '{}': Creating VirtualTopic '{}'...", producerName, destinationName);
        return session.createTopic(virtualTopicName);
    }

    public void produceMessage(String text) {
        try {
            log.info("EventProducer '{}': Sending message <{}>", producerName, text);
            TextMessage textMessage = session.createTextMessage(text);
            producer.send(textMessage);
        } catch (JMSException e) {
            log.error("Could not produce a message with text '{}'", text);
        }
    }

    @Override
    public void stopEventingLifecycleEntity() {
        log.info("Stopping event producer '{}'...", producerName);
        if (!isRunning) {
            log.info("Producer not running - stopping not necessary");
            return;
        }
        try {
            if (producer != null) {
                log.info("Closing producer {}...", producerName);
                producer.close();
            }
            if (session != null) {
                log.info("Closing producer's {} session...", producerName);
                session.close();
            }
            if (connection != null) {
                log.info("Closing producer's {} connection...", producerName);
                connection.close();
            }
            isRunning = false;
        } catch (JMSException e) {
            log.error(String.format("Error while stopping '%s' event consumer!", producerName), e);
        }
    }
}
