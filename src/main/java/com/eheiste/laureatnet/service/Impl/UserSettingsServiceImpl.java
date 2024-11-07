package com.eheiste.laureatnet.service.Impl;

import com.eheiste.laureatnet.model.UserAccount;
import com.eheiste.laureatnet.model.UserSetting;
import com.eheiste.laureatnet.repository.UserSettingsRepository;
import com.eheiste.laureatnet.service.UserSettingsService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserSettingsServiceImpl implements UserSettingsService {
    private final UserSettingsRepository userSettingsRepository;

    @Override
    public Optional<UserSetting> getUserSettings(Long id) {
        UserAccount user = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user.getId() != id) {
            throw new RuntimeException("Unauthorized to access this User Settings");
        }

        return userSettingsRepository.findByUserAccount_Id(id);
    }

    @Override
    public UserSetting updateUserSettings(Long userId, UserSetting userSetting) {
        UserAccount user = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<UserSetting> optionalUserSetting = userSettingsRepository.findByUserAccount_Id(userId);
        UserSetting oldUserSetting;
        if (optionalUserSetting.isPresent()) {
            oldUserSetting = optionalUserSetting.get();
        } else {
            oldUserSetting = UserSetting.builder().userAccount(user).build();
//            oldUserSetting = userSettingsRepository.save(oldUserSetting);
        }

        if (user.getId() != oldUserSetting.getUserAccount().getId()) {
            throw new RuntimeException("Unauthorized to access this User Settings");
        }

        userSetting.setId(oldUserSetting.getId());
        userSetting.setUserAccount(oldUserSetting.getUserAccount());

        return userSettingsRepository.save(userSetting);
    }
}
