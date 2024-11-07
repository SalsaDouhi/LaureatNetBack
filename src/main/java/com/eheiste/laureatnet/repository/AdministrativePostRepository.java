package com.eheiste.laureatnet.repository;

import com.eheiste.laureatnet.model.AdministrativePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministrativePostRepository extends JpaRepository<AdministrativePost, Long> {
}
