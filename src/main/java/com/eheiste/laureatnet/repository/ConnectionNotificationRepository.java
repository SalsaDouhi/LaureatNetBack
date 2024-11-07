package com.eheiste.laureatnet.repository;

import com.eheiste.laureatnet.model.ConnectionNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionNotificationRepository extends JpaRepository<ConnectionNotification, Long> {
}
