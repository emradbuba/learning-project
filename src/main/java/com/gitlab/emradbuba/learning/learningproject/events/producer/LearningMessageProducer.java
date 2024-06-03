package com.gitlab.emradbuba.learning.learningproject.events.producer;

import com.gitlab.emradbuba.learning.learningproject.events.LearningBrokerConfig;
import jakarta.annotation.PreDestroy;
import jakarta.jms.*;
import lombok.RequiredArgsConstructor;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LearningMessageProducer {
    private final LearningBrokerConfig learningBrokerConfig;

    private Connection connection = null;
    private Session session = null;
    private MessageProducer producer = null;

    private void startProducer() throws JMSException {

        // Create ConnectionFactory for a broker using specified credentials...
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                learningBrokerConfig.getBrokerUrl(),
                learningBrokerConfig.getUsername(),
                learningBrokerConfig.getPassword()
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

    @EventListener(ApplicationStartedEvent.class)
    public void onApplicationStart() {
        try {
            System.out.println("[PRODUCER] Starting...");
            startProducer();
            System.out.println("[PRODUCER] Starting... OK");
        } catch (JMSException e) {
            // TODO: Handle the flow...
            System.err.println("[PRODUCER] Starting... FAILED");
            System.err.println(e.getMessage());
        }
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
