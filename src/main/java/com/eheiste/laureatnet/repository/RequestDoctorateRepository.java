package com.eheiste.laureatnet.repository;

import com.eheiste.laureatnet.model.RequestDoctorate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestDoctorateRepository extends JpaRepository<RequestDoctorate, Long> {
}
