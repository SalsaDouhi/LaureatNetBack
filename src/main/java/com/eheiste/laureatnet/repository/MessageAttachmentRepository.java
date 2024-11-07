package com.eheiste.laureatnet.repository;

import com.eheiste.laureatnet.model.MessageAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageAttachmentRepository extends JpaRepository<MessageAttachment, Long> {
    List<MessageAttachment> findAllByMessageId(Long messageId);
}
