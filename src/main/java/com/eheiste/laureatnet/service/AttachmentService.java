package com.eheiste.laureatnet.service;

import java.util.Collection;

import com.eheiste.laureatnet.model.Attachment;

public interface AttachmentService {

    Collection<Attachment> getAllAttachments();

    Attachment findAttachmentById(Long id);

    void deleteAttachmentById(Long id);

    Attachment saveAttachment(Attachment attachment);
}
