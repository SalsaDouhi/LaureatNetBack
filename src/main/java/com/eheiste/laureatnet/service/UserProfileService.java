package com.eheiste.laureatnet.service;

import com.eheiste.laureatnet.dto.viewmodel.ProfilePicturesUpdateDTO;
import com.eheiste.laureatnet.dto.viewmodel.UpdatePassDTO;
import com.eheiste.laureatnet.mapper.UserProfileMapper;
import com.eheiste.laureatnet.model.UserAccount;
import com.eheiste.laureatnet.model.UserProfile;

import java.util.List;
import java.util.Optional;

public interface UserProfileService {
    String updateUserBio(UserAccount userAccount, String bio);

    UserProfile createUserProfile(UserProfile userProfile);

    Optional<UserProfile> getUserProfileById(Long id);

    Optional<UserProfile> getUserProfileByUserAccountId(Long id);

    List<UserProfile> getAllUserProfiles();

    UserProfile updateUserProfile(Long id, UserProfile userProfile);

    void deleteUserProfile(Long id);

    public Optional<UserProfile> changePassword(Long id, UpdatePassDTO updatePaasDTO);

    public UserProfile updateProfilePictures(Long userId, ProfilePicturesUpdateDTO profilePicturesUpdateDTO, UserProfileMapper userProfileMapper);


}
