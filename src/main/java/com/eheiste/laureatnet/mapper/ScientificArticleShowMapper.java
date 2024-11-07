package com.eheiste.laureatnet.mapper;

import org.springframework.stereotype.Component;

import com.eheiste.laureatnet.dto.modelview.ScientificArticlePostShowDTO;
import com.eheiste.laureatnet.dto.modelview.ScientificArticleShowDTO;
import com.eheiste.laureatnet.model.ScientificArticle;
import com.eheiste.laureatnet.model.ScientificArticlePost;



@Component
public class ScientificArticleShowMapper {

    public ScientificArticleShowDTO EntityToDTO(ScientificArticle entity) {
    	ScientificArticleShowDTO dto = new ScientificArticleShowDTO();
    	dto.setId(entity.getId());
    	dto.setPath(entity.getPath());
    	dto.setAuthors(entity.getAuthors());
    	dto.setDatePublished(entity.getDatePublished());
    	dto.setPublisherId(entity.getPublisher().getId());
    	dto.setPublisherName(entity.getPublisher().getEmail());
    	dto.setTitle(entity.getTitle());
        
        return dto;
    }
}
