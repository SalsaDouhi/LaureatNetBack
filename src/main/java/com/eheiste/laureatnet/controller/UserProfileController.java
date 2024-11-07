package com.eheiste.laureatnet.controller;

import com.eheiste.laureatnet.dto.modelview.UserProfileDTOMV;
import com.eheiste.laureatnet.dto.viewmodel.*;
import com.eheiste.laureatnet.model.UserAccount;
import com.eheiste.laureatnet.model.UserProfile;
import com.eheiste.laureatnet.repository.UserAccountRepository;
import com.eheiste.laureatnet.service.UserAccountService;
import com.eheiste.laureatnet.service.UserProfileService;
import com.eheiste.laureatnet.mapper.UserProfileMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user-profiles")
@AllArgsConstructor
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private UserProfileMapper userProfileMapper;

    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private UserAccountRepository userAccountRepository;

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDTOMV> getUserProfileById(@PathVariable Long id) {
        Optional<UserProfile> userProfileOpt = userProfileService.getUserProfileById(id);
        if (userProfileOpt.isPresent()) {
            UserProfileDTOMV userProfileDTO = userProfileMapper.toDTO(userProfileOpt.get());
            return ResponseEntity.ok(userProfileDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserProfileDTOMV> getUserProfileByUserAccountId(@PathVariable Long id) {
        Optional<UserProfile> userProfileOpt = userProfileService.getUserProfileByUserAccountId(id);
        if (userProfileOpt.isPresent()) {
            UserProfileDTOMV userProfileDTO = userProfileMapper.toDTO(userProfileOpt.get());
            return ResponseEntity.ok(userProfileDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping(value = "/change-password/{id}")
    public ResponseEntity<UserProfile> changePassword(@PathVariable Long id, @RequestBody UpdatePassDTO passwordUpdateDTO) {
        // Vérifiez et mettez à jour le mot de passe
//        System.out.println(passwordUpdateDTO);
        Optional<UserProfile> updatedUserProfileOpt = userProfileService.changePassword(id, passwordUpdateDTO);
        if (updatedUserProfileOpt.isPresent()) {
            return ResponseEntity.ok(updatedUserProfileOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/networking/{userId}")
    public ResponseEntity<ProfilNetworking> updateUserProfileNetworking(@PathVariable Long userId, @RequestBody ProfilNetworking profilNetworking) {
        UserAccount user = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        System.out.println(profilNetworking);
        if (user.getId() != userId) {
            throw new RuntimeException("Unauthorized to access this User Settings");
        } else {
            Optional<UserAccount> optionalUserAccount = userAccountRepository.findById(userId);
            if (optionalUserAccount.isPresent()) {
                UserAccount userAccount = optionalUserAccount.get();
                UserProfile userProfile = userProfileMapper.updateUserProfileFromProfilNetworking(userAccount.getUserProfile(), profilNetworking);
                UserProfile updatedUserProfile = userProfileService.updateUserProfile(userProfile.getId(), userProfile);
                ProfilNetworking updatedUserProfileInfo = userProfileMapper.toProfilNetworkingDTO(updatedUserProfile);
                return ResponseEntity.ok(updatedUserProfileInfo);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }
    }

    @PutMapping(value = "/personal-info/{userId}")
    public ResponseEntity<ProfileFirstInfo> updateUserProfile(@PathVariable Long userId, @RequestBody ProfileFirstInfo profileFirstInfo) {
        UserAccount user = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user.getId() != userId) {
            throw new RuntimeException("Unauthorized to access this User Settings");
        } else {
            Optional<UserAccount> optionalUserAccount = userAccountRepository.findById(userId);
            if (optionalUserAccount.isPresent()) {
                UserAccount userAccount = optionalUserAccount.get();
                UserProfile userProfile = userProfileMapper.updateUserProfileFromProfileFirstInfo(userAccount.getUserProfile(), profileFirstInfo);
                UserProfile updatedUserProfile = userProfileService.updateUserProfile(userProfile.getId(), userProfile);
                ProfileFirstInfo updatedUserProfileInfo = userProfileMapper.toProfileFirstInfoDTO(updatedUserProfile);
                return ResponseEntity.ok(updatedUserProfileInfo);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }
    }

    @PutMapping(value = "/update-pictures/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UserProfileDTOMV> updateUserProfile(@PathVariable Long id, @ModelAttribute ProfilePicturesUpdateDTO profilePicturesUpdateDTO) {
        UserAccount user = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user.getId() != id) {
            throw new RuntimeException("Unauthorized to access this User Settings");
        } else {
            UserProfile updatedUserProfile = userProfileService.updateProfilePictures(id, profilePicturesUpdateDTO, userProfileMapper);
            UserProfileDTOMV updatedUserProfileDTOMV = userProfileMapper.toDTO(updatedUserProfile);
            return ResponseEntity.ok(updatedUserProfileDTOMV);
        }
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UserProfileDTOMV> updateUserProfile(@PathVariable Long id, @ModelAttribute UserProfileDTO userProfileDTO) {
        UserAccount user = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user.getId() != id) {
            throw new RuntimeException("Unauthorized to access this User Settings");

        } else {
            Optional<UserProfile> userProfileOpt = userProfileService.getUserProfileById(id);
            if (userProfileOpt.isPresent()) {
                UserProfile userProfile = userProfileMapper.toEntity(userProfileDTO);
                UserProfile updatedUserProfile = userProfileService.updateUserProfile(id, userProfile);
                UserProfileDTOMV updatedUserProfileDTOMV = userProfileMapper.toDTO(updatedUserProfile);
                return ResponseEntity.ok(updatedUserProfileDTOMV);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }
    }

    @PutMapping(value = "/{id}/bio")
    public ResponseEntity<String> updateUserProfile(@PathVariable Long id, @RequestBody String bio) {
        Optional<UserAccount> user = userAccountService.getAccountById(id);
        if (user.isPresent()) {
            userProfileService.updateUserBio(user.get(), bio);
            return ResponseEntity.ok(bio);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
