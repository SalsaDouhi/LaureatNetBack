package com.eheiste.laureatnet.mapper;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eheiste.laureatnet.dto.modelview.CommentsDTO;
import com.eheiste.laureatnet.dto.viewmodel.CommentCreationDTO;
import com.eheiste.laureatnet.model.Comment;
import com.eheiste.laureatnet.model.UserAccount;
import com.eheiste.laureatnet.service.PostService;
import com.eheiste.laureatnet.service.UserAccountService;

@Component
public class CommentsCreationMapper {

	@Autowired
	private UserAccountService userService;
	@Autowired
	private PostService postService;
	
    public Comment toEntity(CommentCreationDTO dto) {
        Comment comment = new Comment();
        
        comment.setContent(dto.getContent());
        UserAccount commentor = userService.loadById(dto.getCommentorId()).get();
        comment.setCommentor(commentor);
        comment.setPost(postService.findPostById(dto.getPostId()).orElseThrow());
        comment.setCreatedAt(LocalDateTime.now());
        return comment;
    }
}