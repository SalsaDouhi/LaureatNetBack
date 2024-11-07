package com.eheiste.laureatnet.controller;

import com.eheiste.laureatnet.model.NoReferenceNotification;
import com.eheiste.laureatnet.service.Impl.NoReferenceNotificationServiceImpl;
import com.eheiste.laureatnet.service.NoReferenceNotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NoReferenceNotificationController {
	@Autowired
    private NoReferenceNotificationService noReferenceNotificationService;

    @GetMapping
    public ResponseEntity<List<NoReferenceNotification>> getAllNoReferenceNotifications() {
        List<NoReferenceNotification> notifications = noReferenceNotificationService.getAllNoReferenceNotifications();
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoReferenceNotification> getNoReferenceNotificationById(@PathVariable("id") Long id) {
        NoReferenceNotification notification = noReferenceNotificationService.getNoReferenceNotificationById(id);
        if (notification != null) {
            return new ResponseEntity<>(notification, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<NoReferenceNotification> createNoReferenceNotification(@RequestBody NoReferenceNotification notification) {
        NoReferenceNotification savedNotification = noReferenceNotificationService.saveNoReferenceNotification(notification);
        return new ResponseEntity<>(savedNotification, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoReferenceNotification(@PathVariable("id") Long id) {
    	noReferenceNotificationService.deleteNoReferenceNotification(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
