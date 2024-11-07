package com.eheiste.laureatnet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eheiste.laureatnet.model.BlockNotification;

@Repository
public interface BlockNotificationRepository extends JpaRepository<BlockNotification, Long> {
	
}