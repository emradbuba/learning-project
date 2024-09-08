# About ActiveMQ

## Messaging Styles
### Basic
What are basic messaging styles in messaging systems also in AMQ?
> <details>
> <summary>Message Queue pattern (Queue / Peer-To-Peer)</summary>
>
> * Also called "point to point"
> * One or many Consumers/Producers binded to the Queue 
> * **Only one consumer** processes the message (like in "Orders" queue - order is processed only once)
> * Message sent to queue is stored until consumed
> * Consumer _acknowledges_ the message
> </details>
> <details>
> <summary>Publish-Subscribe pattern (Topic)</summary>
>
> * Messages sent to a topic and consumers subscribe to this topic, so many can read
> * Messages delivered to all subscribers (consumers)
> * Message can be delivered "live" when consumer is active when message came
> * We can create a durable subscriber so messages will be stored in topic (actually a specific dedicated queue) until consumer/subscription is available/active again. It takes place as long as subscription is not removed (session.unsubscribe())
> </details>

### Extra in AMQ
<details>
<summary>What does it mean a "durable" subscription in AMQ? Is it for Topics and Queues? </summary>

* For topics (publish/subscribe)
* Durable - message is stored - even if everything crashes - until consumer consumes the message
* NonDurable - message is saved only as long as the connection which creates the message exists...
</details>

<details>
<summary>More details about internal durability representation (PubSub approach) /summary>

* In Artemis a **non-durable** subscription to a topic is visible as: 
  * topic itself available under "Addesses"
  * a dedicated queue but with a random name - random, because when connection is off, this queue is removed
* In Artemis a **DURABLE** subscription to a topic, apart from topic, is represented by: 
  * dedicated queue for durable subscriber with name: `ClientID.SubscriptionName`
  * In case of my app, for example: `LearningApp_ChangeEventsConsumer.Sub_ChangeEventsTopic`
</details>

## Effects of different approaches
| Approach | DURABLE                                                                                      | NON-DURABLE | are deleted?
--- |----------------------------------------------------------------------------------------------|-------------| ---
| PeerToPeer | There is one queue on 'addresses' (`QueueName`)<br>and queue for each consumer (`QueueName`) | TODO        | TODO
| PubSub | There is a topic on 'addresses'<br>and queue for each consumer (`ClientID.SubscriptionName`) | TODO        | TODO
| VirtualTopic | test                                                                                         | TODO        | TODO

> <span style='color:#fa0'>***TODO***</span>
> 
> _ // Add screens from Artemis for QUEUES, CONS, PRODS, ADDRS //_ 

## Virtual Topics and their Consumers

This document provides an overview of how virtual topics work in Apache ActiveMQ, including naming conventions, how the broker identifies virtual topics, and when topics and queues are physically created on the broker.

### What Are Virtual Topics?

Virtual topics in Apache ActiveMQ allow you to implement a publish-subscribe mechanism using queues. This approach decouples producers from consumers, providing flexibility and scalability in message distribution.

### Naming Conventions

#### Virtual Topics

- It is a convention, not a requirement, to name virtual topics with the prefix `VirtualTopic.`.
- Example: `VirtualTopic.Orders`

#### Consumer Queues

- Consumer queues should follow the pattern `Consumer.<consumer-name>.<virtual-topic-name>`.
- Example: `Consumer.A.VirtualTopic.Orders`, `Consumer.B.VirtualTopic.Orders`

Using these conventions helps in clearly distinguishing virtual topics from regular topics and makes configuration and troubleshooting easier.

### How ActiveMQ Identifies Virtual Topics

ActiveMQ uses internal configuration and naming conventions to identify virtual topics. This is typically done through the broker's configuration file (`activemq.xml`).

#### Example Configuration

```xml
<destinationInterceptors>
    <virtualDestinationInterceptor>
        <virtualDestinations>
            <virtualTopic name="VirtualTopic.>" prefix="Consumer.*." />
        </virtualDestinations>
    </virtualDestinationInterceptor>
</destinationInterceptors>
```

In this configuration:

`name="VirtualTopic.>"` defines the naming pattern for virtual topics.
`prefix="Consumer.*."` defines the naming pattern for consumer queues.

### When Are Topics and Queues Created?
#### Virtual Topics
Virtual topics themselves are not physically created on the broker. They serve as a routing mechanism to deliver messages to appropriate consumer queues.

#### Consumer Queues
Consumer queues are physically created on the broker when the first consumer subscribes to the queue.

### Detailed Process
#### Producer Sends a Message:

A producer sends a message to a virtual topic (e.g., `VirtualTopic.Orders`).
> <span style='color:#fa0'>***IMPORTANT***</span>
> 
> If no consumers have subscribed to the corresponding consumer queues (e.g., `Consumer.A.VirtualTopic.Orders`), the message is not stored.

#### Consumer Subscribes:

When a consumer subscribes to a queue (e.g., `Consumer.A.VirtualTopic.Orders`), the broker creates the physical queue.
Subsequent messages sent to the virtual topic are routed to the existing consumer queues.

##### Message Routing:

Once the consumer queues are created, messages sent to the virtual topic (e.g., `VirtualTopic.Orders`) are routed and stored in these queues.

### Summary
* Virtual Topics: Logical constructs used for routing, not physically created on the broker.
* Consumer Queues: Physically created when the first consumer subscribes.
* Naming Conventions: Recommended to use VirtualTopic. for virtual topics and Consumer.<name>.<virtual-topic> for consumer queues.

(This part was generated with assistance from ChatGPT, a language model developed by OpenAI.)