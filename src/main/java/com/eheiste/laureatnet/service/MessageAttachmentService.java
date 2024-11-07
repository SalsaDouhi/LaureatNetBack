package com.eheiste.laureatnet.service;

import com.eheiste.laureatnet.model.MessageAttachment;

import java.util.List;

public interface MessageAttachmentService {
    MessageAttachment getMessageAttachment(Long id);
    List<MessageAttachment> getAllMessageAttachmentsByMessageId(Long messageId);
    MessageAttachment createMessageAttachment(MessageAttachment messageAttachment);
    void deleteMessageAttachment(Long id);
}
