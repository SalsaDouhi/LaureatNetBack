package com.eheiste.laureatnet.mapper;

import org.springframework.stereotype.Component;

import com.eheiste.laureatnet.dto.modelview.CommentsDTO;
import com.eheiste.laureatnet.dto.modelview.LikeShowDTO;
import com.eheiste.laureatnet.model.Comment;
import com.eheiste.laureatnet.model.Like;

@Component
public class LikeShowMapper {

    public LikeShowDTO toDTO(Like like) {
    	LikeShowDTO dto = new LikeShowDTO();
        dto.setId(like.getId());
        dto.setLikerId(like.getLiker().getId());
        dto.setLikerName(like.getLiker().getEmail());

        return dto;
    }
}