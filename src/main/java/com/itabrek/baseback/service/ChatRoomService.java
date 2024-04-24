package com.itabrek.baseback.service;

import com.itabrek.baseback.entity.ChatRoom;
import com.itabrek.baseback.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public Optional<Long> getChatId(String senderId, String recipientId, boolean createIfNotExist) {
        Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findBySenderIdAndRecipientId(senderId, recipientId);
        if (optionalChatRoom.isPresent()) {
            return Optional.of(optionalChatRoom.get().getId());
        } else if (createIfNotExist) {
            ChatRoom chatRoom = new ChatRoom();
            chatRoom.setSenderId(senderId);
            chatRoom.setRecipientId(recipientId);
            ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);
            return Optional.of(savedChatRoom.getId());
        } else {
            return Optional.empty();
        }
    }
}
