package com.eheiste.laureatnet.mapper;

import com.eheiste.laureatnet.dto.modelview.UserProfileDTOMV;
import com.eheiste.laureatnet.dto.viewmodel.ProfilNetworking;
import com.eheiste.laureatnet.dto.viewmodel.ProfileFirstInfo;
import com.eheiste.laureatnet.dto.viewmodel.ProfilePicturesUpdateDTO;
import com.eheiste.laureatnet.dto.viewmodel.UserProfileDTO;
import com.eheiste.laureatnet.model.UserProfile;
import com.eheiste.laureatnet.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

@Component
public class UserProfileMapper {
    @Autowired
    private FileStorageService fileStorageService;
    public UserProfile updateUserProfileFromProfilePicturesDTO(UserProfile userProfile, ProfilePicturesUpdateDTO dto) {
        if (dto == null) {
            return userProfile;
        }

        if (dto.getPicture() != null) {
            Map.Entry<String, String> picturePathAndType = fileStorageService.storeFile(dto.getPicture()).entrySet().iterator().next();
            userProfile.setPicture(picturePathAndType.getKey());
        }

        if (dto.getBanner() != null) {
            Map.Entry<String, String> bannerPathAndType = fileStorageService.storeFile(dto.getBanner()).entrySet().iterator().next();
            userProfile.setBanner(bannerPathAndType.getKey());
        }

        return userProfile;
    }
    public UserProfileDTOMV toDTO(UserProfile userProfile) {
        if (userProfile == null) {
            return null;
        }
        UserProfileDTOMV dto = new UserProfileDTOMV();
        dto.setFirstName(userProfile.getFirstName());
        dto.setLastName(userProfile.getLastName());
        LocalDate birthDate = userProfile.getBirthDate();
        dto.setBirthDate(birthDate != null ? birthDate.toString() : null); // Vérifier si la date de naissance est nulle
        dto.setGender(userProfile.isGender());
        dto.setBio(userProfile.getBio());
        dto.setPicture(userProfile.getPicture());
        dto.setBanner(userProfile.getBanner());
        dto.setLocation(userProfile.getLocation());
        dto.setCurrentPosition(userProfile.getCurrentPosition());
        dto.setMajor(userProfile.getMajor());
        dto.setCurrentGrade(userProfile.getCurrentGrade());
        dto.setGraduated(userProfile.getGraduated());
        dto.setWebsite(userProfile.getWebsite());
        dto.setFacebook(userProfile.getFacebook());
        dto.setInstagram(userProfile.getInstagram());
        dto.setTwitter(userProfile.getTwitter());
        dto.setYoutube(userProfile.getYoutube());
        dto.setLinkedin(userProfile.getLinkedin());
        return dto;
    }


    public UserProfile toEntity(UserProfileDTO dto) {
        if (dto == null) {
            return null;
        }
        UserProfile userProfile = new UserProfile();
        userProfile.setFirstName(dto.getFirstName());
        userProfile.setLastName(dto.getLastName());
        String birthDateString = dto.getBirthDate();
        if (birthDateString != null) {
            userProfile.setBirthDate(LocalDate.parse(birthDateString));
        }
        userProfile.setGender(dto.isGender());
        userProfile.setBio(dto.getBio());
        Map.Entry<String,String> PicturePathAndType = fileStorageService.storeFile(dto.getPicture()).entrySet().iterator().next();
        userProfile.setPicture(PicturePathAndType.getKey());
        Map.Entry<String,String> BannerPathAndType = fileStorageService.storeFile(dto.getBanner()).entrySet().iterator().next();
        userProfile.setPicture(BannerPathAndType.getKey());
        userProfile.setBanner(BannerPathAndType.getKey());
        userProfile.setLocation(dto.getLocation());
        userProfile.setCurrentPosition(dto.getCurrentPosition());
        userProfile.setMajor(dto.getMajor());
        userProfile.setCurrentGrade(dto.getCurrentGrade());
        userProfile.setGraduated(dto.getGraduated());
        userProfile.setWebsite(dto.getWebsite());
        userProfile.setFacebook(dto.getFacebook());
        userProfile.setInstagram(dto.getInstagram());
        userProfile.setTwitter(dto.getTwitter());
        userProfile.setYoutube(dto.getYoutube());
        userProfile.setLinkedin(dto.getLinkedin());
        return userProfile;
    }
    public ProfileFirstInfo toProfileFirstInfoDTO(UserProfile userProfile) {
        if (userProfile == null) {
            return null;
        }
        ProfileFirstInfo dto = new ProfileFirstInfo();
        dto.setFirstName(userProfile.getFirstName());
        dto.setLastName(userProfile.getLastName());
        LocalDate birthDate = userProfile.getBirthDate();
        dto.setBirthDate(birthDate != null ? birthDate.toString() : null);
        dto.setLocation(userProfile.getLocation());
        dto.setCurrentGrade(userProfile.getCurrentGrade());
        return dto;
    }

    // Conversion from ProfileFirstInfo to UserProfile
    public UserProfile updateUserProfileFromProfileFirstInfo(UserProfile userProfile, ProfileFirstInfo dto) {
        if (dto == null) {
            return userProfile;
        }
        if (dto.getFirstName() != null) {
            userProfile.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            userProfile.setLastName(dto.getLastName());
        }
        if (dto.getBirthDate() != null) {
            userProfile.setBirthDate(LocalDate.parse(dto.getBirthDate()));
        }
        if (dto.getLocation() != null) {
            userProfile.setLocation(dto.getLocation());
        }
        if (dto.getCurrentGrade() != null) {
            userProfile.setCurrentGrade(dto.getCurrentGrade());
        }
        return userProfile;
    }
    // Methods for ProfilNetworking
    public ProfilNetworking toProfilNetworkingDTO(UserProfile userProfile) {
        if (userProfile == null) {
            return null;
        }
        ProfilNetworking dto = new ProfilNetworking();
        dto.setWebsite(userProfile.getWebsite());
        dto.setFacebook(userProfile.getFacebook());
        dto.setInstagram(userProfile.getInstagram());
        dto.setTwitter(userProfile.getTwitter());
        dto.setYoutube(userProfile.getYoutube());
        dto.setLinkedin(userProfile.getLinkedin());
        return dto;
    }

    public UserProfile updateUserProfileFromProfilNetworking(UserProfile userProfile, ProfilNetworking dto) {
        if (dto == null) {
            return userProfile;
        }
        if (dto.getWebsite() != null) {
            userProfile.setWebsite(dto.getWebsite());
        }
        if (dto.getFacebook() != null) {
            userProfile.setFacebook(dto.getFacebook());
        }
        if (dto.getInstagram() != null) {
            userProfile.setInstagram(dto.getInstagram());
        }
        if (dto.getTwitter() != null) {
            userProfile.setTwitter(dto.getTwitter());
        }
        if (dto.getYoutube() != null) {
            userProfile.setYoutube(dto.getYoutube());
        }
        if (dto.getLinkedin() != null) {
            userProfile.setLinkedin(dto.getLinkedin());
        }
        return userProfile;
    }
}
