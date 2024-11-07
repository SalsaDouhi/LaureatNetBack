package com.eheiste.laureatnet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eheiste.laureatnet.model.SubscriptionNotification;

@Repository
public interface SubscriptionNotificationRepository extends JpaRepository<SubscriptionNotification, Long> {
}