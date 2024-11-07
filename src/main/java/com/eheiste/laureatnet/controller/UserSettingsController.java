package com.eheiste.laureatnet.controller;

import com.eheiste.laureatnet.model.UserSetting;
import com.eheiste.laureatnet.service.UserSettingsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user-settings")
@AllArgsConstructor
public class UserSettingsController {
    private final UserSettingsService userSettingsService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserSetting> getUserSettings(@PathVariable Long userId) {
        Optional<UserSetting> userSettings = userSettingsService.getUserSettings(userId);
        return userSettings.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserSetting> updateUserSettings(@PathVariable Long userId, @RequestBody UserSetting userSetting) {
        UserSetting savedUserSettings = userSettingsService.updateUserSettings(userId, userSetting);
        return new ResponseEntity<>(savedUserSettings, HttpStatus.CREATED);
    }
}
