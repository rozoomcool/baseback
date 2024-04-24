package com.itabrek.baseback.repository;

import com.itabrek.baseback.entity.ChatMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends CrudRepository<ChatMessage, Long> {
}
