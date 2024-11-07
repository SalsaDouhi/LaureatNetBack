package com.eheiste.laureatnet.repository;

import com.eheiste.laureatnet.model.NoReferenceNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoReferenceNotificationRepository  extends JpaRepository<NoReferenceNotification, Long> {
}

