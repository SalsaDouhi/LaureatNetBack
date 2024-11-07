package com.eheiste.laureatnet.service;

import java.util.Collection;

import com.eheiste.laureatnet.model.SubscriptionNotification;

public interface SubscriptionNotificationService {

    Collection<SubscriptionNotification> getAllSubscriptionNotifications();

    SubscriptionNotification findSubscriptionNotificationById(Long id);

    void deleteSubscriptionNotificationById(Long id);

    SubscriptionNotification saveSubscriptionNotification(SubscriptionNotification subscriptionNotification);

    SubscriptionNotification updateSubscriptionNotification(Long id, SubscriptionNotification updatedSubscriptionNotification);
}