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

        log.info("EventConsumer '{}': Starting...", consumerName);
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                eventConsumerSettingsCore.getBrokerUrl(),
                eventConsumerSettingsCore.getBrokerUsername(),
                eventConsumerSettingsCore.getBrokerPassword()
        );

        log.info("EventConsumer '{}': Creating connection...", consumerName);
        connection = connectionFactory.createConnection();
        connection.setClientID("TestClientId"); // TODO: <-- get it from user configurable settings
        connection.start();

        log.info("EventConsumer '{}': Creating session...", consumerName);
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE); // <-- TODO: Ack modes, transacted?

        Destination destination = createSourceDestination();

        log.info("EventConsumer '{}': Creating AMQ message consumer...", consumerName);
        consumer = session.createConsumer(destination); // TODO: durable / shared / consumer ??

        log.info("EventConsumer '{}': Adding message listener...", consumerName);
        consumer.setMessageListener(new MessageListener() { // TODO: listener should be separate
            @Override
            public void onMessage(Message message) {
                if (message instanceof TextMessage) {
                    try {
                        TextMessage textMessage = (TextMessage) message;
                        String s = textMessage.getText();
                        log.info("Consuming message: <{}>", s);
                        textMessage.acknowledge();
                    } catch (JMSException e) {
                        log.error("Could not read message...");
                    }
                }
            }
        });
        log.info("EventConsumer '{}': STARTED SUCCESSFULLY...", consumerName);
    }

    private Destination createSourceDestination() throws JMSException {
        String sourceName = eventConsumerSettingsCore.getSourceName();
        if (eventConsumerSettingsCore.getEventCommunicationModel() == EventCommunicationModel.VIA_QUEUE) {
            log.info("EventConsumer '{}': Creating queue '{}'...", consumerName, sourceName);
            return session.createQueue(sourceName);
        }
        if (eventConsumerSettingsCore.getEventCommunicationModel() == EventCommunicationModel.VIA_TOPIC) {
            log.info("EventConsumer '{}': Creating topic '{}'...", consumerName, sourceName);
            return session.createTopic(sourceName);
        }
        String virtualTopicSourceName = AMQ_VIRTUAL_TOPIC_PREFIX + sourceName;
        log.info("EventConsumer '{}': Creating VirtualTopic '{}'...", consumerName, virtualTopicSourceName);
        return session.createTopic(virtualTopicSourceName);
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
