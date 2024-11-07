package com.eheiste.laureatnet.service.Impl;

import com.eheiste.laureatnet.model.Message;
import com.eheiste.laureatnet.model.MessageAttachment;
import com.eheiste.laureatnet.repository.MessageAttachmentRepository;
import com.eheiste.laureatnet.repository.MessageRepository;
import com.eheiste.laureatnet.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {
    private MessageRepository messageRepository;
    private MessageAttachmentRepository messageAttachmentRepository;

    @Override
    public Message getMessage(Long id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));
    }

    @Override
    public List<Message> getAllMessagesByUserId(Long userId) {
        return messageRepository.findAllBySenderId(userId);
    }

    @Override
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public Message updateMessage(Long id, String content) {
        Message existing = getMessage(id);
        existing.setContent(content);
        existing.setModifiedAt(LocalDateTime.now());
        return messageRepository.save(existing);
    }

    @Override
    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }

    @Override
    public Page<Message> getMessagesBetweenUsers(Long userId1, Long userId2, Pageable pageable) {
        return messageRepository.findMessagesBetweenUsers(userId1, userId2, pageable);
    }

    @Override
    @Transactional
    public Message createMessageWithAttachments(Message message, List<MessageAttachment> attachments) {
        Message savedMessage = messageRepository.save(message);
        if (attachments != null && !attachments.isEmpty()) {
            for (MessageAttachment attachment : attachments) {
                attachment.setMessage(savedMessage);
                messageAttachmentRepository.save(attachment);
            }
        }
        return savedMessage;
    }
}
