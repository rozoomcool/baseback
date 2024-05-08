package com.itabrek.baseback.service.notification;

import com.itabrek.baseback.entity.ChatMessage;
import com.itabrek.baseback.entity.GlobalMessage;

public interface NotificationService {
    void sendGlobalNotification(GlobalMessage message);
    void sendPrivateNotification(ChatMessage message);
}
