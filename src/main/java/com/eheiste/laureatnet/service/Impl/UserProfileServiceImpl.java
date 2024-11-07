package com.eheiste.laureatnet.service.Impl;

import com.eheiste.laureatnet.dto.viewmodel.ProfilePicturesUpdateDTO;
import com.eheiste.laureatnet.dto.viewmodel.UpdatePassDTO;
import com.eheiste.laureatnet.mapper.UserProfileMapper;
import com.eheiste.laureatnet.model.UserAccount;
import com.eheiste.laureatnet.model.UserProfile;
import com.eheiste.laureatnet.repository.UserAccountRepository;
import com.eheiste.laureatnet.repository.UserProfileRepository;
import com.eheiste.laureatnet.service.UserProfileService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String updateUserBio(UserAccount userAccount, String bio) {
        UserProfile profile = userAccount.getUserProfile();
        profile.setBio(bio);
        userProfileRepository.save(profile);
        return bio;
    }

    @Override
    public UserProfile createUserProfile(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }

    @Override
    public Optional<UserProfile> getUserProfileById(Long id) {
        return userProfileRepository.findById(id);
    }

    @Override
    public Optional<UserProfile> getUserProfileByUserAccountId(Long id) {
        return userProfileRepository.findByUserAccount_Id(id);
    }

    @Override
    public List<UserProfile> getAllUserProfiles() {
        return userProfileRepository.findAll();
    }

    @Override
    public UserProfile updateUserProfile(Long id, UserProfile userProfile) {
        // Recherche du profil existant par son identifiant
        UserProfile existingProfile = userProfileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("UserProfile not found with id: " + id));

        // Mise à jour des champs avec les nouvelles valeurs du profil passé en paramètre
        existingProfile.setFirstName(userProfile.getFirstName());
        existingProfile.setLastName(userProfile.getLastName());
        existingProfile.setBirthDate(userProfile.getBirthDate());
        existingProfile.setGender(userProfile.isGender());
        existingProfile.setBio(userProfile.getBio());
        existingProfile.setPicture(userProfile.getPicture());
        existingProfile.setBanner(userProfile.getBanner());
        existingProfile.setLocation(userProfile.getLocation());
        existingProfile.setCurrentPosition(userProfile.getCurrentPosition());
        existingProfile.setMajor(userProfile.getMajor());
        existingProfile.setCurrentGrade(userProfile.getCurrentGrade());
        existingProfile.setGraduated(userProfile.getGraduated());
        existingProfile.setWebsite(userProfile.getWebsite());
        existingProfile.setFacebook(userProfile.getFacebook());
        existingProfile.setInstagram(userProfile.getInstagram());
        existingProfile.setTwitter(userProfile.getTwitter());
        existingProfile.setYoutube(userProfile.getYoutube());
        existingProfile.setLinkedin(userProfile.getLinkedin());

        // Enregistrer les modifications dans la base de données et retourner le profil mis à jour
        return userProfileRepository.save(existingProfile);
    }

    public UserProfile updateProfilePictures(Long userId, ProfilePicturesUpdateDTO profilePicturesUpdateDTO, UserProfileMapper userProfileMapper) {
        UserAccount user = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserProfile userProfile = user.getUserProfile();
        if (userProfile != null) {
            userProfile = userProfileMapper.updateUserProfileFromProfilePicturesDTO(userProfile, profilePicturesUpdateDTO);
            return userProfileRepository.save(userProfile);
        } else {
            throw new EntityNotFoundException("UserProfile not found with id " + userId);
        }
    }

    @Override
    public void deleteUserProfile(Long id) {
        userProfileRepository.deleteById(id);
    }

    public Optional<UserProfile> changePassword(Long userId, UpdatePassDTO updatePaasDTO) {
        UserAccount user = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getId() != userId) {
            // trying to change the password of an account that isn't his
            return Optional.empty();
        }

        Optional<UserAccount> optionalUserAccount = userAccountRepository.findById(userId);
        if (optionalUserAccount.isPresent()) {
            UserAccount userAccount = optionalUserAccount.get();
            if (passwordEncoder.matches(updatePaasDTO.getOldPassword(), userAccount.getPassword())){
                String encodedNewPassword = passwordEncoder.encode(updatePaasDTO.getNewPassword());
                userAccount.setPassword(encodedNewPassword);
                userAccountRepository.save(userAccount);
                return Optional.of(userAccount.getUserProfile());
            }
        }
//        Optional<UserProfile> userProfileOptional = userProfileRepository.findById(id);
//        if (userProfileOptional.isPresent()) {
//            UserProfile userProfile = userProfileOptional.get();
//            // Vérifiez si l'ancien mot de passe correspond
//            if (passwordEncoder.matches(updatePaasDTO.getOldPassword(), userProfile.getUserAccount().getPassword())) {
//                // Encodez et mettez à jour le nouveau mot de passe
//                String encodedNewPassword = passwordEncoder.encode(updatePaasDTO.getNewPassword());
//                userProfile.getUserAccount().setPassword(encodedNewPassword);
//                return Optional.of(userProfileRepository.save(userProfile));
//            }
//        }
        return Optional.empty();
    }
}
