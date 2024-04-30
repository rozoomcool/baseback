package com.itabrek.baseback.websocket;

import com.itabrek.baseback.repository.RedisRepository;
import com.itabrek.baseback.repository.SessionRedisRepositoryImpl;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ConnectionWebSocketHandler extends TextWebSocketHandler {
    private SessionRedisRepositoryImpl repository;

    public ConnectionWebSocketHandler(SessionRedisRepositoryImpl repository) {
        this.repository = repository;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        repository.add(session.getId(), session);
    }
}
