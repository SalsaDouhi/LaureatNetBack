package com.eheiste.laureatnet.service;

import com.eheiste.laureatnet.model.UserSetting;

import java.util.Optional;

public interface UserSettingsService {
    Optional<UserSetting> getUserSettings(Long id);
    UserSetting updateUserSettings(Long userId, UserSetting userSetting);
}
