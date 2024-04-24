package com.itabrek.baseback.repository;

import com.itabrek.baseback.entity.ChatRoom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends CrudRepository<ChatRoom, Long> {
    Optional<ChatRoom> findBySenderIdAndRecipientId(String senderId, String recipientId);
}
