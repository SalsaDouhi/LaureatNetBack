package com.eheiste.laureatnet.service;

import com.eheiste.laureatnet.dto.chat.MessageRequest;

public interface ChatSocketService {
    void sendUserConversationByUserId(Long userId);

    void sendMessagesByConversationId(Long conversationId);

    void saveMessage(MessageRequest msg);

    void deleteConversationByConversationId(Long conversationId);

    void deleteMessageByMessageId(Long conversationId, Long messageId);
}
