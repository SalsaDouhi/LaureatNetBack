package com.eheiste.laureatnet.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageResponse {
    Long messageId;
    Long senderId;
    Long receiverId;
    String content;
    Date createdAt;
}