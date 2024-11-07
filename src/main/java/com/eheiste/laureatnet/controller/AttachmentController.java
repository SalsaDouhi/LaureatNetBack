package com.eheiste.laureatnet.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eheiste.laureatnet.model.Attachment;
import com.eheiste.laureatnet.service.AttachmentService;

@RestController
@RequestMapping("/api/v1/attachments")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @GetMapping
    public Collection<Attachment> getAllAttachments() {
        return attachmentService.getAllAttachments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Attachment> getAttachment(@PathVariable long id) {
        Attachment attachment = attachmentService.findAttachmentById(id);
        if (attachment != null) {
            return ResponseEntity.ok(attachment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteAttachment(@PathVariable long id) {
        attachmentService.deleteAttachmentById(id);
    }

    @PostMapping
    public ResponseEntity<Attachment> createAttachment(@RequestBody Attachment attachment) {
        return ResponseEntity.status(HttpStatus.CREATED).body(attachmentService.saveAttachment(attachment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Attachment> updateAttachment(@PathVariable long id, @RequestBody Attachment attachment) {
        Attachment existingAttachment = attachmentService.findAttachmentById(id);
        if (existingAttachment != null) {
            existingAttachment.setType(attachment.getType());
            existingAttachment.setPath(attachment.getPath());
            existingAttachment.setOrder(attachment.getOrder());
            attachmentService.saveAttachment(existingAttachment);
            return ResponseEntity.ok(existingAttachment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
