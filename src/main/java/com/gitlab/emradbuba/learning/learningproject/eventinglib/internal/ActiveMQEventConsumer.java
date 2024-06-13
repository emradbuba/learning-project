package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal;

import jakarta.annotation.PreDestroy;
import jakarta.jms.*;
import lombok.RequiredArgsConstructor;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActiveMQEventConsumer {
    private final EventingProperties eventingProperties;

    private Connection connection = null;
    private Session session = null;
    private MessageConsumer consumer = null;

    private void startListening() throws JMSException {
        // Create ConnectionFactory for a broker using specified credentials...
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                eventingProperties.getBrokerUrl(),
                eventingProperties.getUsername(),
                eventingProperties.getPassword()
        );

        // Get a connection from connectionFactory and start it....
        connection = connectionFactory.createConnection();
        connection.start();

        // Create a session having a connection...
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create a Queue or Topic (will be created automatically by ActiveMQ if it doesn't exist)
        Queue queue = session.createQueue("exampleQueue");

        // Create a MessageConsumer from the Session to the Queue
        consumer = session.createConsumer(queue);
        // Listen for incoming messages
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message instanceof TextMessage) {
                    try {
                        TextMessage textMessage = (TextMessage) message;
                        String s = textMessage.getText();
                        System.out.println("Consuming message: " + s);
                        textMessage.acknowledge();
                    } catch (JMSException e) {
                        System.err.println("Could not read message...");
                    }
                }
            }
        });
    }

    @EventListener(ApplicationStartedEvent.class)
    private void onApplicationStart() {
        try {
            System.out.println("Starting AMQ consumer...");
            startListening();
            System.out.println("Starting AMQ consumer... SUCCESS");
        } catch (JMSException e) {
            // TODO: Handle the flow...
            System.err.println("Starting AMQ consumer... FAILURE");
            System.err.println(e.getMessage());
        }
    }

    @PreDestroy // TODO: ok?
    private void stopListening() {
        try {

            if (consumer != null) {
                consumer.close();

                System.out.println("JMS Cleanup... consumer closed");
            }
            if (session != null) {
                session.close();
                System.out.println("JMS Cleanup... session closed");
            }
            if (connection != null) {
                System.out.println("JMS Cleanup... connection closed");
                connection.close();
            }
            System.out.println("JMS Cleanup... SUCCESS");
        } catch (JMSException e) {
            System.err.println("JMS Cleanup... FAILED");
        }
    }
}
