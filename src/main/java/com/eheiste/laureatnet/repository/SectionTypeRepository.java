package com.eheiste.laureatnet.repository;

import com.eheiste.laureatnet.model.SectionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionTypeRepository extends JpaRepository<SectionType, Long> {
}
