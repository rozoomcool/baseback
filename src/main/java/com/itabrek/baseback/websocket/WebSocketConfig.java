package com.itabrek.baseback.websocket;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.itabrek.baseback.configuration.UserInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
@Controller
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableStompBrokerRelay("/topic", "/queue/private")
                .setRelayHost("localhost")
                //?connection_attempts=5&retry_delay=5
//                .setRelayPort(5672)
                .setRelayPort(61613)
              .setUserDestinationBroadcast("/topic/unresolved.user")
                .setUserRegistryBroadcast("/topic/registry.broadcast")
                .setClientLogin("guest")
                .setClientPasscode("guest");
//                .setSystemLogin("guest") // логин для доступа к RabbitMQ
//                .setSystemPasscode("guest") // пароль для доступа к RabbitMQ
//                .setSystemHeartbeatSendInterval(5000) // интервал отправки пульсации RabbitMQ (мс)
//                .setSystemHeartbeatReceiveInterval(4000);
//        config.enableSimpleBroker("/topic", "/queue/private");
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/ws").setAllowedOrigins("*");
        registry.addEndpoint("/ws").addInterceptors(new HttpHandshakeInterceptor()).setAllowedOrigins("*");
//        registry.addEndpoint("/secured/room").setAllowedOrigins("*");
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
        resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(new ObjectMapper());
        converter.setContentTypeResolver(resolver);
        messageConverters.add(converter);
        return false;
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}