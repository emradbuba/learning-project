AMQ-10
* [ ] Support for VirtualTopics
* [ ] Notes for: 
  * [ ] Support Peer
  * [ ] Support Topics
  * [ ] Support Virtual Topics
  * [ ] What is created on Broker, is it persistent?
* [ ] Extract to an external library
* [ ] Sample consumer, sample producer (each for single example)
* [ ] AMQ Broker in docker compose, so we can start the application as it is with not external configs
  * [ ] No need to store messages between Artemis docker runs
  * [ ] It should be possible to "turn off" and on an application to check how messages are stored

Corner cases to cover:
- AMQ broker restarts
- Application (producer or consumer) stops and reconnects