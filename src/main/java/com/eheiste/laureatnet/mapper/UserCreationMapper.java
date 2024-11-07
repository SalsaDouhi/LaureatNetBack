package com.eheiste.laureatnet.mapper;

import com.eheiste.laureatnet.dto.viewmodel.UserCreationDTO;
import com.eheiste.laureatnet.model.UserAccount;
import com.eheiste.laureatnet.model.UserProfile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserCreationMapper {

    public UserAccount toUserAccount(UserCreationDTO userCreationDTO) {
        return UserAccount.builder()
                .createdAt(LocalDateTime.now())
                .email(userCreationDTO.getEmail())
                .build();
    }

    public UserProfile toUserProfile(UserCreationDTO userCreationDTO) {
        return UserProfile.builder()
                .firstName(userCreationDTO.getFirstName())
                .lastName(userCreationDTO.getLastName())
                .gender(userCreationDTO.getGender())
                .birthDate(userCreationDTO.getBirthDate())
                .major(userCreationDTO.getMajor())
                .currentGrade(userCreationDTO.getCurrentGrade())
                .build();
    }
}
