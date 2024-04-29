package com.itabrek.baseback.repository;

import java.util.Map;
import java.util.Optional;

public interface RedisRepository<T, A> {
    Map<T, A> findAll();
    void add(T key, A value);
    void delete(T key);
    Optional<A> findByKey(T key);
}