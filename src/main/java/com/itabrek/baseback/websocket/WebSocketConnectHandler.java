package com.itabrek.baseback.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.stereotype.Component;

@Component
public class WebSocketConnectHandler implements ApplicationListener<SessionConnectedEvent> {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void onApplicationEvent(SessionConnectedEvent event) {
        String sessionId = event.getMessage().getHeaders().get("simpSessionId").toString();
        String username = event.getUser().getName();
        System.out.println(username);
        String destination = "/user/" + username + "/queue/private";
        this.messagingTemplate.convertAndSend(destination, "Hello, " + username);
    }
}

