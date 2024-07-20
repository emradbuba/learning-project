package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.lifecycle.EventHandlingEntityLifecycle;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.settings.EventConsumerSettingsCore;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.EventConsumer;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventCommunicationModel;
import jakarta.annotation.PreDestroy;
import jakarta.jms.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

import static com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.ActiveMQEventProducer.AMQ_VIRTUAL_TOPIC_PREFIX;

@Slf4j
public class ActiveMQEventConsumer implements EventConsumer, EventHandlingEntityLifecycle {

    private final EventConsumerSettingsCore eventConsumerSettingsCore;
    private final String consumerName;

    private Connection connection = null;
    private Session session = null;
    private MessageConsumer consumer = null;

    public ActiveMQEventConsumer(EventConsumerSettingsCore eventConsumerSettingsCore) {
        this.eventConsumerSettingsCore = eventConsumerSettingsCore;
        this.consumerName = eventConsumerSettingsCore.getConsumerName();
    }

    private void startListening() throws JMSException {
        // Create ConnectionFactory for a broker using specified credentials...
        log.info("Starting the event consumer '{}'...", consumerName);
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                eventConsumerSettingsCore.getBrokerUrl(),
                eventConsumerSettingsCore.getBrokerUsername(),
                eventConsumerSettingsCore.getBrokerPassword()
        );

        // Get a connection from connectionFactory and start it....
        connection = connectionFactory.createConnection();
        connection.start();

        // Create a session having a connection...
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create a Queue or Topic (will be created automatically by ActiveMQ if it doesn't exist)
        Destination destination = createSourceDestination();

        // Create a MessageConsumer from the Session to the Queue
        consumer = session.createConsumer(destination);
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

    private Destination createSourceDestination() throws JMSException {
        String sourceName = eventConsumerSettingsCore.getSourceName();
        if (eventConsumerSettingsCore.getEventCommunicationModel() == EventCommunicationModel.VIA_QUEUE) {
            log.info("Creating AMQ queue '{}'...", sourceName);
            return session.createQueue(sourceName);
        }
        if (eventConsumerSettingsCore.getEventCommunicationModel() == EventCommunicationModel.VIA_TOPIC) {
            log.info("Creating AMQ topic '{}'...", sourceName);
            return session.createTopic(sourceName);
        }
        log.info("Creating AMQ virtual topic '{}'...", sourceName);
        return session.createTopic(AMQ_VIRTUAL_TOPIC_PREFIX + sourceName);
    }

    @EventListener(ApplicationStartedEvent.class)
    @Override
    public void start() {
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

    @PreDestroy
    @Override
    public void stop() {
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
