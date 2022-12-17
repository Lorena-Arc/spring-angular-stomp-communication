package org.example;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class Config implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // every path will start with this
        config.setApplicationDestinationPrefixes("/stomp");
        // as we are using the simple in memory broker, we can also define custom prefixes
        // (the convention is nice to follow tho)
        config.enableSimpleBroker("/topic", "/queue");
        // the messages send to the user will be sent to this path
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // here we add the connectig endpoint (e.g. client must connect to www.bla.bla/entry)
        registry.addEndpoint("/entry")
                .setAllowedOriginPatterns("*")
                .withSockJS();
        registry.addEndpoint("/entry");
    }
}
