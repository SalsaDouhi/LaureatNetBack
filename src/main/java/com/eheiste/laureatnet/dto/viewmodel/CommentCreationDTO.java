package com.eheiste.laureatnet.dto.viewmodel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentCreationDTO {
	@NotBlank(message = "commentaire sans contenu")
	private String content;
	
	@NotNull(message = "Id commentateure manquant")
	private Long commentorId;	
	
	@NotNull(message = "Id post manquant")
	private Long postId;
}
