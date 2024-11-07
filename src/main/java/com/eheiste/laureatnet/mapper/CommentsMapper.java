package com.eheiste.laureatnet.mapper;

import org.springframework.stereotype.Component;

import com.eheiste.laureatnet.dto.modelview.CommentsDTO;
import com.eheiste.laureatnet.model.Comment;

@Component
public class CommentsMapper {

    public CommentsDTO toDTO(Comment comment) {
        CommentsDTO dto = new CommentsDTO();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setUserId(comment.getCommentor().getId());
        dto.setUserFullName(comment.getCommentorFullName());
        dto.setUserUsername(comment.getCommentor().getUsername());
        dto.setUserFullName(comment.getCommentor().getUserProfile().getFirstName()+' '+comment.getCommentor().getUserProfile().getLastName());
        dto.setUserProfileImage(comment.getCommentor().getUserProfile().getPicture());
        dto.setCreatedAt(comment.getCreatedAt());
        return dto;
    }
}