package com.eheiste.laureatnet.service.Impl;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eheiste.laureatnet.model.Attachment;
import com.eheiste.laureatnet.repository.AttachmentRepository;
import com.eheiste.laureatnet.service.AttachmentService;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Override
    public Collection<Attachment> getAllAttachments() {
        return attachmentRepository.findAll();
    }

    @Override
    public Attachment findAttachmentById(Long id) {
        Optional<Attachment> attachmentOptional = attachmentRepository.findById(id);
        return attachmentOptional.orElse(null);
    }

    @Override
    public void deleteAttachmentById(Long id) {
        attachmentRepository.deleteById(id);
    }

    @Override
    public Attachment saveAttachment(Attachment attachment) {
        return attachmentRepository.save(attachment);
    }
}
