# About ActiveMQ

## Virtual Topics and Consumers

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