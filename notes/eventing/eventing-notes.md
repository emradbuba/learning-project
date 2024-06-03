# Eventing - basic notes

> <span style='color:#fa0'>***TODO***</span>
> 
> Read: https://docs.oracle.com/javaee/7/tutorial/jms-concepts003.htm#BNCEH 

## Basics

### Basic JMS
JMS is just a standard. There are also different inplementations of this standard like `ActiveMQ`, `ActiveMQ 6.0 (Artemis)`, `RabbitMQ`...

#### Basic terms

<details>
<summary> <i>See the image with terms</i> </summary>

> ![img_2.png](img_2.png) 
</details>

<details>
<summary>What is a <code>JMSProvider</code>?</summary>

> It is just a message broker which implements the JMS standard (interface and communication between producers and consumers).
> Examples: ActiveMQ, Rabbit, IBM MQ...
</details>

<details>
<summary>What is a <code>JMSClient</code>?</summary>

> An application / service which acts as a **producer or consumer**
</details>

<details>
<summary>Where does the producer send events to?</summary>

> To a destination - a topic or a queue - depending on the model. 
</details>

<details>
<summary>What is the <code>JMSDestination</code></summary>

> As mentioned above - a topic or queue. Consumers consumes messages from a destination. 
> It is an object in application represents a place for producers and consumers.
</details>

<details>
<summary>Who creates a <code>JMSDestination</code></summary>

> JMSProvider
</details>

<details>
<summary>What are main two types of destinations?</summary>

> * Topics - for publisher/subscriber model
> * Queue - for point-to-point approach
</details>

<details>
<summary>What is <code>JMSConnectionFactory</code>?</summary>

> Object used by JMSClient to connect to JMSProvider (message broker)
</details>

<details>
<summary>What is <code>JMSConnection</code></summary>

> Represents an open connection to a message broker. It is created by the JMSConnectionFactory
</details>

<details>
<summary>What is <code>JMSSession</code></summary>

> Context (single-threaded) for producing and consuming messages where producers and consumers can be created, also messages can be created and tranastion management is taking place.
</details>

<details>
<summary>What is <code>JMSMessageProducer</code> and <code>JMSMessageConsumer</code></summary>

> Created by JMSSession. Sends messages to a destination (topic or queue).
</details>

#### Communication models

<details>
<summary>What are the main models of communication?</summary>

> Source: https://docs.oracle.com/javaee/7/tutorial
> <details>
> <summary>Point-to-point</summary>
> 
> > ![img.png](img.png)
> * Each message --> one consumer
> * The receiver can fetch the message whether or not it was running when the client sent the message
> </details>
>
> <details>
> <summary>Published / Subscriber</summary>
>
> > ![img_1.png](img_1.png)
> * Each message can have multiple consumers
> * A client that subscribes to a topic can consume only messages sent after the client has created a subscription, and the consumer must continue to be active in order for it to consume messages
> </details>
</details>

<details>
<summary>What is a durable subscription?</summary>

> By default, in topic based model, subscriber can receive messages sent after it has subscribed itself to a topic.
>
> Durable subscription enables to get rid of this requirement, but it has to be configured first.
</details>


#### Other
<details>
<summary>Main responsibilities of a broker</summary>

> * Routing and delivery
> * Persistence
> * Transactions
> * Security
> * Providing administrative/management tools and mechanisms
</details>

<details>
<summary>The role of a <code>MessageListener</code></summary>

> When defining a consumer, wh can configure/set an implementation of a MessageListener, so the
> application listens for an incoming messages from the destination. The `onMessage()` method does the work.
</details>

<details>
<summary>Would ActiveMQ create a destination automatically?</summary>

> Yes, ActiveMQ can automatically create a queue when a client defines it, **provided that** 
> the broker is configured to allow dynamic destination creation.
> 
> By default, ActiveMQ is set up to create destinations (queues and topics) dynamically 
> when they are first referenced by a producer or consumer.
</details>

<details>
<summary>Syncho vs Asynchro consumption</summary>

> * Synchro - using `receive()` method blocking until a message comes or timeout is reached
> * Asynch - using a message listener for waiting to incoming messages
</details>