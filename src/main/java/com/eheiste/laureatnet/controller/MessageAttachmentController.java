package com.eheiste.laureatnet.controller;

import com.eheiste.laureatnet.model.MessageAttachment;
import com.eheiste.laureatnet.service.MessageAttachmentService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message-attachments")
@AllArgsConstructor
@NoArgsConstructor
public class MessageAttachmentController {
    private MessageAttachmentService messageAttachmentService;

    @GetMapping("/{id}")
    public ResponseEntity<MessageAttachment> getMessageAttachment(@PathVariable Long id) {
        try {
            MessageAttachment messageAttachment = messageAttachmentService.getMessageAttachment(id);
            return ResponseEntity.ok(messageAttachment);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-message/{messageId}")
    public List<MessageAttachment> getMessageAttachmentsByMessageId(@PathVariable Long messageId) {
        return messageAttachmentService.getAllMessageAttachmentsByMessageId(messageId);
    }

    @PostMapping()
    public ResponseEntity<MessageAttachment> createMessageAttachment(@RequestBody MessageAttachment messageAttachment) {
        try {
            MessageAttachment newAttachment = messageAttachmentService.createMessageAttachment(messageAttachment);
            return ResponseEntity.ok(newAttachment);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessageAttachment(@PathVariable Long id) {
        try {
            messageAttachmentService.deleteMessageAttachment(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
