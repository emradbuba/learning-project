* Set information like ClientId, QueueName etc... so it is better visible on Broker console:
  * Who created a consumer
  * Name of the queue: Consumer.LearningProject.VirtualTopic.CommonEvents ?
* Test different communication models
  * Pure Queue (tylko jeden consumer? tylko jeden producer?)
  * Pure Topic
  * DurableSubscription using Topic approach
  * VirtualTopics
  * More about ACKN
* Support reconnection when broker is down, also on start
* Message should be an object
* Improve logging (connections, reconnections, disconnections)
* Extract MessageListener to a separate configurable class (user specified listeners, canHandle, handle)
* Code review - refactor, common code etc...
