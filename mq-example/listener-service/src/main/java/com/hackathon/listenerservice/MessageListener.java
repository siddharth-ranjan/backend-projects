package com.hackathon.listenerservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    private static final Logger log = LoggerFactory.getLogger(MessageListener.class);
    public static final String QUEUE_NAME = "message.queue";

    @RabbitListener(queues = QUEUE_NAME)
    public void receiveMessage(String message) {
        log.info("New message received: '{}'", message);
    }
}