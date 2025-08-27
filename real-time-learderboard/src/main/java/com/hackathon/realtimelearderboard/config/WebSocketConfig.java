package com.hackathon.realtimelearderboard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Defines the prefix for topics that clients can subscribe to.
        // The server will broadcast messages to destinations with this prefix.
        config.enableSimpleBroker("/topic");

        // Defines the prefix for destinations that receive messages from clients.
        // E.g., a client message to "/app/hello" would be routed to a @MessageMapping("/hello") method.
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Registers the "/ws" endpoint for WebSocket connections.
        // withSockJS() provides a fallback for browsers that don't support WebSockets.
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
    }
}
