package com.eheiste.laureatnet.service.Impl;

import com.eheiste.laureatnet.dto.chat.ConversationResponse;
import com.eheiste.laureatnet.dto.chat.MessageRequest;
import com.eheiste.laureatnet.dto.chat.MessageResponse;
import com.eheiste.laureatnet.dto.chat.WebSocketResponse;
import com.eheiste.laureatnet.model.Conversation;
import com.eheiste.laureatnet.model.Message;
import com.eheiste.laureatnet.model.UserAccount;
import com.eheiste.laureatnet.repository.ConversationRepository;
import com.eheiste.laureatnet.repository.MessageRepository;
import com.eheiste.laureatnet.repository.UserAccountRepository;
import com.eheiste.laureatnet.service.ChatSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatSocketServiceImpl implements ChatSocketService {
    private final SimpMessagingTemplate messagingTemplate;
    private final UserAccountRepository userAccountRepository;
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;

    // Send user conversations to a specific user by their user ID through a web socket.
    @Override
    public void sendUserConversationByUserId(Long userId) {
        List<ConversationResponse> conversation = conversationRepository.findConversationsByUserAccountId(userId);
        messagingTemplate.convertAndSend(
                "/topic/user/".concat(String.valueOf(userId)),
                WebSocketResponse.builder()
                        .type("ALL")
                        .data(conversation)
                        .build()
        );
    }

    // Send messages of a specific conversation to the connected users through a web socket.
    @Override
    public void sendMessagesByConversationId(Long conversationId) {
        Conversation conversation = new Conversation();
        conversation.setId(conversationId);
        List<Message> messageList = messageRepository.findAllByConversationOrderByCreatedAtDesc(conversation);
        List<MessageResponse> messageResponseList = messageList.stream()
                .map((message -> MessageResponse.builder()
                        .messageId(message.getId())
                        .content(message.getContent())
                        .createdAt(Date.from(message.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant()))
                        .senderId(message.getSender().getId())
                        .receiverId(message.getReceiver().getId())
                        .build())
                ).toList();
        messagingTemplate.convertAndSend("/topic/conv/".concat(String.valueOf(conversationId)), WebSocketResponse.builder()
                .type("ALL")
                .data(messageResponseList)
                .build()
        );
    }

    // Save a new message using a web socket.
    @Override
    public void saveMessage(MessageRequest msg) {
        UserAccount sender = userAccountRepository.findById(msg.getSenderId()).get();
        UserAccount receiver = userAccountRepository.findById(msg.getReceiverId()).get();
        Conversation conversation = conversationRepository.findConversationByUsers(sender, receiver).get();
        Message newMessage = new Message();
        newMessage.setContent(msg.getContent());
        newMessage.setCreatedAt(msg.getCreatedAt());
        newMessage.setConversation(conversation);
        newMessage.setSender(sender);
        newMessage.setReceiver(receiver);
        Message savedMessage = messageRepository.save(newMessage);
        // notify listener
        MessageResponse res = MessageResponse.builder()
                .messageId(savedMessage.getId())
                .content(savedMessage.getContent())
                .createdAt(Date.from(savedMessage.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant()))
                .senderId(savedMessage.getSender().getId())
                .receiverId(savedMessage.getReceiver().getId())
                .build();
        messagingTemplate.convertAndSend("/topic/conv/".concat(msg.getConversationId().toString()),
                WebSocketResponse.builder()
                        .type("ADDED")
                        .data(res)
                        .build()
        );
        sendUserConversationByUserId(msg.getSenderId());
        sendUserConversationByUserId(msg.getReceiverId());
    }

    // Delete a conversation by ID using a web socket.
    @Transactional
    @Override
    public void deleteConversationByConversationId(Long conversationId) {
        Conversation c = new Conversation();
        c.setId(conversationId);
        messageRepository.deleteAllByConversation(c);
        conversationRepository.deleteById(conversationId);
    }

    // Delete a message by ID within a conversation using a web socket.
    @Override
    public void deleteMessageByMessageId(Long conversationId, Long messageId) {
        messageRepository.deleteById(messageId);
        // notify listener
        sendMessagesByConversationId(conversationId);
    }
}
