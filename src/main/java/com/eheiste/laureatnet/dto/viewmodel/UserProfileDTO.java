package com.eheiste.laureatnet.dto.viewmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {
    private String firstName;
    private String lastName;
    private String birthDate;
    private boolean gender;
    private String bio;
    private MultipartFile picture;
    private MultipartFile banner;
    private String location;
    private String currentPosition;
    private String major;
    private String currentGrade;
    private String graduated;
    private String website;
    private String facebook;
    private String instagram;
    private String twitter;
    private String youtube;
    private String linkedin;
}

