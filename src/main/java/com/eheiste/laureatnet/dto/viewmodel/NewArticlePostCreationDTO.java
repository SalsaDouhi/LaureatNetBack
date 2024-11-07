package com.eheiste.laureatnet.dto.viewmodel;


import java.io.File;
import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
public class NewArticlePostCreationDTO {
	
	@NotNull(message="mandatory")
	private Long posterId;	
	
	@NotNull(message="mandatory")
	private MultipartFile article;
	
	@NotBlank(message="mandatory")
    private String content;
		
	@NotBlank(message="mandatory")
    private String title;
	
	@NotNull(message="mandatory")
    private LocalDate datePublished;
	
	@NotBlank(message="mandatory")
    private String authors;
}
