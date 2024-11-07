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

import com.eheiste.laureatnet.model.SubscriptionNotification;
import com.eheiste.laureatnet.service.SubscriptionNotificationService;

@RestController
@RequestMapping("/api/v1/subscription-notifications")
public class SubscriptionNotificationController {

    @Autowired
    private SubscriptionNotificationService subscriptionNotificationService;

    @GetMapping
    public Collection<SubscriptionNotification> getAllSubscriptionNotifications() {
        return subscriptionNotificationService.getAllSubscriptionNotifications();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionNotification> getSubscriptionNotificationById(@PathVariable("id") Long id) {
        SubscriptionNotification subscriptionNotification = subscriptionNotificationService.findSubscriptionNotificationById(id);
        if (subscriptionNotification != null) {
            return ResponseEntity.ok(subscriptionNotification);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscriptionNotificationById(@PathVariable("id") Long id) {
        subscriptionNotificationService.deleteSubscriptionNotificationById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<SubscriptionNotification> createSubscriptionNotification(@RequestBody SubscriptionNotification subscriptionNotification) {
        SubscriptionNotification createdSubscriptionNotification = subscriptionNotificationService.saveSubscriptionNotification(subscriptionNotification);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubscriptionNotification);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionNotification> updateSubscriptionNotification(@PathVariable("id") Long id, @RequestBody SubscriptionNotification subscriptionNotification) {
        SubscriptionNotification updatedSubscriptionNotification = subscriptionNotificationService.updateSubscriptionNotification(id, subscriptionNotification);
        if (updatedSubscriptionNotification != null) {
            return ResponseEntity.ok(updatedSubscriptionNotification);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
