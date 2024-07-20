package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.lifecycle.EventHandlingEntityLifecycle;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.settings.EventProducerSettingsCore;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.EventProducer;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventCommunicationModel;
import jakarta.jms.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import java.util.UUID;

@Slf4j
public class ActiveMQEventProducer implements EventProducer, EventHandlingEntityLifecycle {

    public static final String AMQ_VIRTUAL_TOPIC_PREFIX = "VirtualTopic.";
    private final EventProducerSettingsCore eventingPropertiesInternal;
    private final String producerName;

    private Connection connection = null;
    private Session session = null;
    private MessageProducer producer = null;
    private boolean isRunning = false;

    public ActiveMQEventProducer(EventProducerSettingsCore eventingPropertiesInternal) {
        this.producerName = eventingPropertiesInternal.getProducerName();
        this.eventingPropertiesInternal = eventingPropertiesInternal;
    }

    @Override
    public void start() {
        try {
            if (!isRunning) {
                startProducer();
            }
        } catch (JMSException e) {
            log.error(String.format("Cannot start event producer '%s'", producerName), e);
        }
    }

    private void startProducer() throws JMSException {
        log.info("Starting the event producer '{}'...", producerName);
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                eventingPropertiesInternal.getBrokerUrl(),
                eventingPropertiesInternal.getBrokerUsername(),
                eventingPropertiesInternal.getBrokerPassword()
        );

        // Get a connection from connectionFactory and start it....
        connection = connectionFactory.createConnection();
        connection.start();

        // Create a session having a connection...
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create a Queue or Topic (will be created automatically by ActiveMQ if it doesn't exist)
        Destination producerDestination = createProducerDestination();

        // Create a MessageConsumer from the Session to the Destination
        producer = session.createProducer(producerDestination);
        log.info("Successfully created AMQ message producer '{}'", producerName);
        isRunning = true;
    }

    private Destination createProducerDestination() throws JMSException {
        String destinationName = eventingPropertiesInternal.getDestinationName();
        if (eventingPropertiesInternal.getEventCommunicationModel() == EventCommunicationModel.VIA_QUEUE) {
            log.info("Creating AMQ queue '{}'...", destinationName);
            return session.createQueue(destinationName);
        }
        if (eventingPropertiesInternal.getEventCommunicationModel() == EventCommunicationModel.VIA_TOPIC) {
            log.info("Creating AMQ topic '{}'...", destinationName);
            return session.createTopic(destinationName);
        }
        log.info("Creating AMQ virtual topic '{}'...", destinationName);
        return session.createTopic(AMQ_VIRTUAL_TOPIC_PREFIX + destinationName);
    }

    public void produceMessage() throws JMSException {
        String randomString = UUID.randomUUID().toString().substring(0, 8);
        System.out.println("Sending message <" + randomString + ">");
        TextMessage textMessage = session.createTextMessage(randomString);
        producer.send(textMessage);
    }

    @Override
    public void stop() {
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
