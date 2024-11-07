package com.eheiste.laureatnet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eheiste.laureatnet.model.Attachment;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
