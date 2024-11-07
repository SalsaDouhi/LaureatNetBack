package com.eheiste.laureatnet.controller;

import com.eheiste.laureatnet.model.Message;
import com.eheiste.laureatnet.model.MessageAttachment;
import com.eheiste.laureatnet.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
@AllArgsConstructor
public class MessageController {
    private MessageService messageService;

    private final Path rootLocation = Paths.get("uploaded-files");

    //    @GetMapping("/{id}")
//    public ResponseEntity<Message> getMessage(@PathVariable Long id) {
//        try {
//            Message message = messageService.getMessage(id);
//            return ResponseEntity.ok(message);
//        } catch (RuntimeException ex) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//

    @GetMapping()
    public ResponseEntity<List<Message>> getMessages() {
        List<Message> messages = messageService.getAllMessages();

        if(messages.size() == 0)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(messages);
    }

    @GetMapping("/user/{userId}")
    public List<Message> getMessagesByUserId(@PathVariable Long userId) {
        return messageService.getAllMessagesByUserId(userId);
    }



    @GetMapping("/conversations")
    public ResponseEntity<Page<Message>> getChatMessages(
            @RequestParam Long userId1,
            @RequestParam Long userId2,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Message> messages = messageService.getMessagesBetweenUsers(userId1, userId2, pageable);
        return ResponseEntity.ok(messages);
    }

    @PostMapping(value = "/", consumes = "multipart/form-data")
    public ResponseEntity<Message> createMessage(
            @RequestParam("message") String messageJson,
            @RequestParam(value = "files", required = false) List<MultipartFile> files) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(messageJson, Message.class);
        // todo: use tokenBearer as sender
        // todo: verify that receiver accepts messages from non friends
        // todo: verify that sender & receiver are friends

        // stores files and create MessageAttachment object for each of them >:c
        List<MessageAttachment> attachments = processFiles(files, message);

        Message createdMessage = messageService.createMessageWithAttachments(message, attachments);
        return ResponseEntity.ok(createdMessage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable Long id, @RequestParam String content) {
        try {
            Message updatedMessage = messageService.updateMessage(id, content);
            return ResponseEntity.ok(updatedMessage);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        try {
            // todo: delete related messageAttachment and files
            messageService.deleteMessage(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // this function will:
    // 1. call storeFile function to store the files
    // 2. create a MessageAttachment with the path + generated order
    private List<MessageAttachment> processFiles(List<MultipartFile> files, Message message) throws IOException {
        List<MessageAttachment> attachments = new ArrayList<>();
        if (files != null) {
            for (MultipartFile file : files) {
                String fileName = storeFile(file);
                MessageAttachment attachment = new MessageAttachment();
                attachment.setMessage(message);
                attachment.setType(file.getContentType());
                attachment.setPath(fileName);
                attachment.setAttachment_order(attachments.size() + 1);
                attachments.add(attachment);
            }
        }
        return attachments;
    }

    private String storeFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("Failed to store empty file " + file.getOriginalFilename());
        }

        Path destinationFile = this.rootLocation.resolve(
                        Paths.get(file.getOriginalFilename()))
                .normalize().toAbsolutePath();

        if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
            throw new IOException("Cannot store file outside current directory.");
        }
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile,
                    StandardCopyOption.REPLACE_EXISTING);
        }
        return destinationFile.toString();
    }
}
