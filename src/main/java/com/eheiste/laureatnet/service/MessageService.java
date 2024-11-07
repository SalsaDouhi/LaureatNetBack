package com.eheiste.laureatnet.service;

import com.eheiste.laureatnet.model.Message;
import com.eheiste.laureatnet.model.MessageAttachment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MessageService {
    Message getMessage(Long id);

    List<Message> getAllMessagesByUserId(Long userId);
    List<Message> getAllMessages();

    Message createMessage(Message message);

    Message updateMessage(Long id, String content);

    void deleteMessage(Long id);
    Page<Message> getMessagesBetweenUsers(Long userId1, Long userId2, Pageable pageable);

    Message createMessageWithAttachments(Message message, List<MessageAttachment> attachments);


}
