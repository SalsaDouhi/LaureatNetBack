package com.eheiste.laureatnet.mapper;

import org.springframework.stereotype.Component;

import com.eheiste.laureatnet.dto.modelview.ScientificArticlePostShowDTO;
import com.eheiste.laureatnet.dto.modelview.ScientificArticleShowDTO;
import com.eheiste.laureatnet.model.ScientificArticle;
import com.eheiste.laureatnet.model.ScientificArticlePost;

@Component
public class ScientificArticlePostShowMapper {
	
    public ScientificArticlePostShowDTO EntityToDTO(ScientificArticlePost entity) {
    	ScientificArticlePostShowDTO dto = new ScientificArticlePostShowDTO();
    	
    	dto.setPath(entity.getArticle().getPath());
    	dto.setAuthors(entity.getArticle().getAuthors());
    	dto.setDatePublished(entity.getArticle().getDatePublished());
    	dto.setPublisherId(entity.getPoster().getId());
    	dto.setPublisherName(entity.getPoster().getEmail());
    	dto.setTitle(entity.getArticle().getTitle());
    	
    	dto.setId(entity.getId());
    	dto.setContent(entity.getContent());
    	dto.setPostCreatedAt(entity.getCreatedAt());
    	dto.setLikesCount(entity.getLikesCount());
    	dto.setPostModifiedAt(entity.getModifiedAt());
    	
        return dto;
    }

}
