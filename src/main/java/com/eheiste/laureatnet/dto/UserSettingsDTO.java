package com.eheiste.laureatnet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSettingsDTO {
    private int acceptMsgs;
    private int acceptNotifications;
}

