package com.eheiste.laureatnet.repository;

import com.eheiste.laureatnet.model.UserSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSettingsRepository extends JpaRepository<UserSetting, Long> {
    Optional<UserSetting> findByUserAccount_Id(Long id);
}
