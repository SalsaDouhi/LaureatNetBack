package com.eheiste.laureatnet.service.Impl;

import com.eheiste.laureatnet.model.MessageAttachment;
import com.eheiste.laureatnet.repository.MessageAttachmentRepository;
import com.eheiste.laureatnet.service.MessageAttachmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageAttachmentServiceImpl implements MessageAttachmentService {
    private MessageAttachmentRepository messageAttachmentRepository;

    @Override
    public MessageAttachment getMessageAttachment(Long id) {
        return messageAttachmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MessageAttachment not found"));
    }

    @Override
    public List<MessageAttachment> getAllMessageAttachmentsByMessageId(Long messageId) {
        // Assuming a method in MessageAttachmentRepository to find attachments by message ID
        return messageAttachmentRepository.findAllByMessageId(messageId);
    }

    @Override
    public MessageAttachment createMessageAttachment(MessageAttachment messageAttachment) {
        return messageAttachmentRepository.save(messageAttachment);
    }

    @Override
    public void deleteMessageAttachment(Long id) {
        messageAttachmentRepository.deleteById(id);
    }
}
