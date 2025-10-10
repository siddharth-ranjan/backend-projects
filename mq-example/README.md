Project Overview: Simple Message Broadcast System with Spring Boot & RabbitMQ

Objective 🎯
The goal of this project is to create two minimal Spring Boot applications: a Publisher and a Listener, both interacting through RabbitMQ. The focus is solely on learning how message queues (MQ) work by implementing a basic message broadcasting system.

The Two Microservices

Message Publisher Service (The Sender)
Functionality: This service provides a simple REST endpoint (e.g., POST /send).
Role: When a message is sent to this endpoint (using a tool like Postman), the Publisher sends the message to a RabbitMQ queue (message.queue). It doesn’t care or know who will consume the message.

Message Listener Service (The Receiver)
Functionality: This service has no API endpoints. It runs as a background worker.
Role: The Listener connects to the same RabbitMQ queue (message.queue) on startup. It continuously listens for messages and, once a message is available, it picks it up and logs it to the console (e.g., INFO: New message received: 'Hello from the publisher!').

The Flow
Send Request: You send a message to the Publisher Service's /send endpoint using an API client like Postman.
Publish to Queue: The Publisher Service connects to RabbitMQ and places the message into a queue (message.queue).
Receive from Queue: The Listener Service, which is already listening to message.queue, detects the new message.
Log Message: The Listener Service logs the message to its console, allowing you to see the content of the message.

Project Architecture

Publisher:
Exposes a simple HTTP POST endpoint.
Connects to RabbitMQ to publish messages.

Listener:
Runs as a background service, constantly listening to RabbitMQ.
Processes the message and logs it.
