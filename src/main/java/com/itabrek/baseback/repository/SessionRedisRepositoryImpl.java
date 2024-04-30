package com.itabrek.baseback.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class SessionRedisRepositoryImpl implements RedisRepository<String, WebSocketSession>{
    private static final String KEY = "SESSION:";
    private final RedisTemplate<String, WebSocketSession> redisTemplate;

    @Override
    public Map<String, WebSocketSession> findAll() {
        Set<String> keys = redisTemplate.keys(KEY + '*');
        if (keys == null)
            return Map.of();
        Map<String, WebSocketSession> result = new HashMap<>();
        for (String key : keys) {
            findByKey(key).ifPresent(webSocketSession -> result.put(key, webSocketSession));
        }
        return result;
    }

    @Override
    public void add(String key, WebSocketSession value) {
        redisTemplate.opsForValue().set(KEY + key, value);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(KEY + key);
    }

    @Override
    public Optional<WebSocketSession> findByKey(String key) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(KEY + key));
    }
}
