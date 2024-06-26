package com.itabrek.baseback.controller;

import com.itabrek.baseback.entity.ChatMessage;
import com.itabrek.baseback.entity.ChatNotification;
import com.itabrek.baseback.model.Greeting;
import com.itabrek.baseback.service.ChatMessageService;
import com.itabrek.baseback.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;

    @MessageMapping("/send")
    @SendTo("/topic/chat")
    public String sendMessage(String message) {
        return message;
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting(@Payload Greeting mes) {
        System.out.println(":::::::" + mes);
        return mes.getMessage();
    }

    @MessageMapping("/private")
//    @SendToUser("/queue/private")
    public void sendPrivateMessage(@Payload ChatMessage message) {
        messagingTemplate.convertAndSendToUser(message.getRecipientName(), "/queue/private", message);
    }
}