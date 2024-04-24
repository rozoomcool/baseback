package com.itabrek.baseback.controller;

import com.itabrek.baseback.entity.ChatMessage;
import com.itabrek.baseback.entity.ChatNotification;
import com.itabrek.baseback.service.ChatMessageService;
import com.itabrek.baseback.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

//@RestController
//@RequestMapping("/chat")
//@RequiredArgsConstructor
//public class ChatController {
//
//    private final SimpMessagingTemplate messagingTemplate;
//    private final ChatMessageService chatMessageService;
//    private final ChatRoomService chatRoomService;
//
//    @MessageMapping("/chat")
//    public void processMessage(@Payload ChatMessage chatMessage) {
//        var chatId = chatRoomService
//                .getChatId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true);
//        chatMessage.setChatId(chatId.get().toString());
//
//        ChatMessage saved = chatMessageService.save(chatMessage);
//
//        messagingTemplate.convertAndSendToUser(
//                chatMessage.getRecipientId(),"/queue/messages",
//                new ChatNotification(
//                        saved.getId(),
//                        saved.getSenderId(),
//                        saved.getSenderName()));
//    }

//    @MessageMapping("/sock")
//    public void processMessage(Message message) {
//        messagingTemplate.convertAndSend(message);
//    }
//
//    @SendTo("/topic/messages")
//    public Message send(Message message) throws Exception {
//        String time = new SimpleDateFormat("HH:mm").format(new Date());
//        return new Message(message.getFrom(), message.getText());
//    }
//}