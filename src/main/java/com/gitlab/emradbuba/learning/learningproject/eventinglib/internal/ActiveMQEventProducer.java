package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.settings.EventProducerSettingsInternal;
import jakarta.annotation.PreDestroy;
import jakarta.jms.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import java.util.UUID;

@Slf4j
public class ActiveMQEventProducer {

    private final EventProducerSettingsInternal eventingPropertiesInternal;
    private final String producerName;

    public ActiveMQEventProducer(EventProducerSettingsInternal eventingPropertiesInternal) {
        // TODO: Create AMQ producer using internal settings...
        this.eventingPropertiesInternal = eventingPropertiesInternal;
        this.producerName = eventingPropertiesInternal.getProducerName();
    }

    private Connection connection = null;
    private Session session = null;
    private MessageProducer producer = null;

    public void start() {
        try {
            startProducer();
        } catch (JMSException e) {
            System.err.println("[PRODUCER] Starting... FAILED");
            System.err.println(e.getMessage());
        }
    }

    private void startProducer() throws JMSException {

        // Create ConnectionFactory for a broker using specified credentials...
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
        Queue queue = session.createQueue("exampleQueue");

        // Create a MessageConsumer from the Session to the Queue
        producer = session.createProducer(queue);
    }

    public void produceMessage() throws JMSException {
        String randomString = UUID.randomUUID().toString().substring(0, 8);
        System.out.println("Sending message <" + randomString + ">");
        TextMessage textMessage = session.createTextMessage(randomString);
        producer.send(textMessage);
    }



    @PreDestroy // TODO: ok?
    private void stopListening() {
        try {

            if (producer != null) {
                producer.close();

                System.out.println("[PRODUCER] JMS Cleanup... Producer closed");
            }
            if (session != null) {
                session.close();
                System.out.println("[PRODUCER] JMS Cleanup... Producer closed");
            }
            if (connection != null) {
                System.out.println("[PRODUCER] JMS Cleanup... Producer closed");
                connection.close();
            }
            System.out.println("[PRODUCER] JMS Cleanup... SUCCESS");
        } catch (JMSException e) {
            System.err.println("[PRODUCER] JMS Cleanup... FAILED");
        }
    }
}
