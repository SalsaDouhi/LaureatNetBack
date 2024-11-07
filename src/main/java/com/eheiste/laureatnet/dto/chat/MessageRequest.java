package com.eheiste.laureatnet.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {
    Long conversationId;
    Long senderId;
    Long receiverId;
    String content;
    LocalDateTime createdAt;
}
