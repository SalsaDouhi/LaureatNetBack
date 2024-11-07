package com.eheiste.laureatnet.dto.viewmodel;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JobOfferCreationDTO {

    @NotNull(message = "mandatory")
    private Long posterId;

    @NotBlank(message = "mandatory")
    private String content;

    @NotNull(message = "mandatory")
    private Long entrepriseId;

    @NotNull(message = "mandatory")
    private LocalDate startDate;

    private LocalDate endDate;

    @NotBlank(message = "mandatory")
    private String jobTitle;

    private List<MultipartFile> attachments;
    
    private List<Long> technologyList ;

}
