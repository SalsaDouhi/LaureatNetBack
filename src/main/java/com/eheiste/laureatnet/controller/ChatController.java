package com.eheiste.laureatnet.controller;

import com.eheiste.laureatnet.dto.chat.MessageRequest;
import com.eheiste.laureatnet.service.ChatSocketService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import org.springframework.messaging.handler.annotation.MessageMapping;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/chat")
@AllArgsConstructor
public class ChatController {
    private final ChatSocketService chatSocketService;

    @MessageMapping("/user")
    public void sendUserConversationByUserId(Long userId) {
        chatSocketService.sendUserConversationByUserId(userId);
    }

    @MessageMapping("/conv")
    public void sendMessagesByConversationId(Long conversationId) {
        chatSocketService.sendMessagesByConversationId(conversationId);
    }

    @MessageMapping("/sendMessage")
    public void saveMessage(MessageRequest message) {
        chatSocketService.saveMessage(message);
    }

    @MessageMapping("/deleteMessage")
    public void deleteMessage(Map<String, Object> payload) {
        Long conversationId = ((Integer) payload.get("conversationId")).longValue();
        Long messageId = ((Integer) payload.get("messageId")).longValue();
        chatSocketService.deleteMessageByMessageId(conversationId, messageId);
    }
}
