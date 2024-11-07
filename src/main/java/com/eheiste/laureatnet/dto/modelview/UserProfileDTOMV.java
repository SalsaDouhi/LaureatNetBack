package com.eheiste.laureatnet.dto.modelview;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTOMV {
    private String firstName;
    private String lastName;
    private String birthDate;
    private boolean gender;
    private String bio;
    private String picture;
    private String banner;
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

