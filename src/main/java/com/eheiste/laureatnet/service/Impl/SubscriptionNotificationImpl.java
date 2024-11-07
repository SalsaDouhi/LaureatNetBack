package com.eheiste.laureatnet.service.Impl;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eheiste.laureatnet.model.SubscriptionNotification;
import com.eheiste.laureatnet.repository.SubscriptionNotificationRepository;
import com.eheiste.laureatnet.service.SubscriptionNotificationService;

@Service
public class SubscriptionNotificationImpl implements SubscriptionNotificationService {

    @Autowired
    private SubscriptionNotificationRepository subscriptionNotificationRepository;

    @Override
    public Collection<SubscriptionNotification> getAllSubscriptionNotifications() {
        return subscriptionNotificationRepository.findAll();
    }

    @Override
    public SubscriptionNotification findSubscriptionNotificationById(Long id) {
        Optional<SubscriptionNotification> subscriptionNotificationOptional = subscriptionNotificationRepository.findById(id);
        return subscriptionNotificationOptional.orElse(null);
    }

    @Override
    public void deleteSubscriptionNotificationById(Long id) {
        subscriptionNotificationRepository.deleteById(id);
    }

    @Override
    public SubscriptionNotification saveSubscriptionNotification(SubscriptionNotification subscriptionNotification) {
        return subscriptionNotificationRepository.save(subscriptionNotification);
    }

    @Override
    public SubscriptionNotification updateSubscriptionNotification(Long id, SubscriptionNotification updatedSubscriptionNotification) {
        SubscriptionNotification existingSubscriptionNotification = findSubscriptionNotificationById(id);
        if (existingSubscriptionNotification != null) {
            existingSubscriptionNotification.setContent(updatedSubscriptionNotification.getContent());
            existingSubscriptionNotification.setRead(updatedSubscriptionNotification.isRead());
            existingSubscriptionNotification.setReadAt(updatedSubscriptionNotification.getReadAt());
            existingSubscriptionNotification.setUser(updatedSubscriptionNotification.getUser());
            existingSubscriptionNotification.setReferencedSubscription(updatedSubscriptionNotification.getReferencedSubscription());
            return subscriptionNotificationRepository.save(existingSubscriptionNotification);
        }
        return null;
    }
}
