package com.itabrek.baseback.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatNotification {
    private Long id;
    private String senderId;
    private String senderName;
}