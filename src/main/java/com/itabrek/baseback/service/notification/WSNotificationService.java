package com.itabrek.baseback.service.notification;

import com.itabrek.baseback.entity.ChatMessage;
import com.itabrek.baseback.entity.GlobalMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WSNotificationService implements NotificationService {
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendGlobalNotification(GlobalMessage message) {
        messagingTemplate.convertAndSend("/topic/global", message);
    }

    @Override
    public void sendPrivateNotification(ChatMessage message) {
        messagingTemplate.convertAndSendToUser(message.getRecipientName(), "/queue/private", message);
    }
}
