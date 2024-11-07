package com.eheiste.laureatnet.repository;

import com.eheiste.laureatnet.model.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportTypeRepository extends JpaRepository<ReportType, Long> {
}
