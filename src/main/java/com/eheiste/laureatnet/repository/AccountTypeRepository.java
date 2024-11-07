package com.eheiste.laureatnet.repository;

import com.eheiste.laureatnet.model.AccountType;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {
    Optional<AccountType> findByTitle(String title);
}