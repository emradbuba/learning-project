## About ActiveMQ

<details>
<summary>Mechanism protecting from delivering poison messages over and over?</summary>

> * **Redelivery delay** - give consumer some time to recover
> * **DLQ** - after N failed deliveries, send to DeadLetterQueue and do not send anymore
</details>

<details>
<summary>Redelivery in Artemis?</summary>

> Configured on the broker under broker.xml
> see docu: https://activemq.apache.org/components/artemis/documentation/latest/undelivered-messages.html
</details>

