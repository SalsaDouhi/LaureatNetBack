package com.eheiste.laureatnet.mapper;

import com.eheiste.laureatnet.dto.ProfileDTO;
import com.eheiste.laureatnet.dto.UserAccountDTO;
import com.eheiste.laureatnet.model.UserAccount;
import com.eheiste.laureatnet.model.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class UserAccountMapper {
    public static ProfileDTO mapUserAccountToDTO(UserAccount userAccount) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setUserId(userAccount.getId());
        profileDTO.setUserProfile(userAccount.getUserProfile());
        profileDTO.setIsOwner(false);
        return profileDTO;
    }

    public UserAccountDTO mapToUserAccountDTO(UserProfile userProfile) {
        UserAccountDTO userAccountMV = new UserAccountDTO();
        userAccountMV.setEmail(userProfile.getUserAccount().getEmail());
        userAccountMV.setFirstName(userProfile.getFirstName());
        userAccountMV.setLastName(userProfile.getLastName());
        userAccountMV.setDate(userProfile.getBirthDate()); // Convertir LocalDate en String si nécessaire
        userAccountMV.setGender(userProfile.isGender());
        userAccountMV.setId(userProfile.getUserAccount().getId());
        userAccountMV.setMajor(userProfile.getMajor());
        userAccountMV.setCurrentGrade(userProfile.getCurrentGrade());
        userAccountMV.setRole(userProfile.getUserAccount().getAccountType().getTitle());

        return userAccountMV;
    }

    public ProfileDTO toProfileDTO(UserAccount userAccount) {
        return ProfileDTO.builder()
                .userId(userAccount.getId())
                .userProfile(userAccount.getUserProfile())
                .email(userAccount.getEmail())
                .isOwner(false)
                .build();
    }
}
