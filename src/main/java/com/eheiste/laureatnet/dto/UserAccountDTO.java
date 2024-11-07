package com.eheiste.laureatnet.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountDTO {
    private Long id;
    @NotNull(message = "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh")
    @Email(message = "L'adresse e-mail doit être valide")
    private String email;
    @NotNull(message = "firstName ne peut pas être vide")
    private String firstName;
    @NotNull(message = "lastName ne peut pas être vide")
    private String lastName;
    private LocalDate date;
    @NotNull(message = "gender ne peut pas être vide")
    private boolean gender;
    @NotNull(message = "role ne peut pas être vide")
    private String role;
    private String currentGrade;
    private String major;
}
