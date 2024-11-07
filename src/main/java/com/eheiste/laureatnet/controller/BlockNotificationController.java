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

import com.eheiste.laureatnet.model.BlockNotification;
import com.eheiste.laureatnet.service.BlockNotificationService;

@RestController
@RequestMapping("/api/v1/block-notifications")
public class BlockNotificationController {

    @Autowired
    private BlockNotificationService blockNotificationService;

    @GetMapping
    public Collection<BlockNotification> getAllBlockNotifications() {
        return blockNotificationService.getAllBlockNotifications();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlockNotification> getBlockNotificationById(@PathVariable("id") Long id) {
        BlockNotification blockNotification = blockNotificationService.findBlockNotificationById(id);
        if (blockNotification != null) {
            return ResponseEntity.ok(blockNotification);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlockNotificationById(@PathVariable("id") Long id) {
        blockNotificationService.deleteBlockNotificationById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<BlockNotification> createBlockNotification(@RequestBody BlockNotification blockNotification) {
        BlockNotification createdBlockNotification = blockNotificationService.saveBlockNotification(blockNotification);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBlockNotification);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlockNotification> updateBlockNotification(@PathVariable("id") Long id, @RequestBody BlockNotification blockNotification) {
        BlockNotification updatedBlockNotification = blockNotificationService.updateBlockNotification(id, blockNotification);
        if (updatedBlockNotification != null) {
            return ResponseEntity.ok(updatedBlockNotification);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
