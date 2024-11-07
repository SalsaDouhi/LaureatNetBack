package com.eheiste.laureatnet.dto.viewmodel;


import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
public class ExistingArticlePostCreationDTO {
	
	@NotNull(message="mandatory")
	private Long posterId;	
	
	@NotNull(message="mandatory")
	private Long articleId;
	
	@NotBlank(message="mandatory")
    private String content;
		

}
