package com.eheiste.laureatnet.repository;

import com.eheiste.laureatnet.model.SectionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionItemRepository extends JpaRepository<SectionItem, Long> {
    List<SectionItem> findByUserAccount_IdOrderByEndDateDesc(Long userAccountId);
}
