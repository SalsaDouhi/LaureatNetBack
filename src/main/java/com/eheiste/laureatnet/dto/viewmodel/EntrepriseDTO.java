package com.eheiste.laureatnet.dto.viewmodel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize
public class EntrepriseDTO {

	@NotBlank(message = "Le titre ne peut pas être vide")
    private String title;
    private MultipartFile logo;
    private double localisationX;
    private double localisationY;
    @NotBlank(message = "Le numéro de téléphone ne peut pas être vide")
    private String tel;
    @NotBlank(message = "L'email ne peut pas être vide")
    private String email;
    @NotBlank(message = "Le website  ne peut pas être vide")
    private String website;

}
