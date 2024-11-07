package com.eheiste.laureatnet.dto.viewmodel;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserCreationDTO {
    Long id;
    String email;
    String firstName;
    String lastName;
    LocalDate birthDate;
    Boolean gender;
    String role;
    Long roleId;
    String major;
    String currentGrade;
}
