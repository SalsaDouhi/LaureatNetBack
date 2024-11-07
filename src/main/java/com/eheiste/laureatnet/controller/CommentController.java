package com.eheiste.laureatnet.controller;

import com.eheiste.laureatnet.dto.modelview.CommentsDTO;
import com.eheiste.laureatnet.dto.viewmodel.CommentCreationDTO;
import com.eheiste.laureatnet.mapper.CommentsCreationMapper;
import com.eheiste.laureatnet.mapper.CommentsMapper;
import com.eheiste.laureatnet.model.Comment;
import com.eheiste.laureatnet.model.UserAccount;
import com.eheiste.laureatnet.service.CommentService;
import com.eheiste.laureatnet.service.Impl.CommentServiceImpl;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Service
@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
	
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentsCreationMapper commentMapper;
    @Autowired
    private CommentsMapper commentsMapper;
    @GetMapping
    public List<CommentsDTO> getAllComments(@RequestParam(required = false) Long postId){
    	if(postId==null) {
    		return commentService.findAllComments();
        }return commentService.findAllCommentsByPostId(postId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        return commentService.findCommentById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CommentsDTO> createComment(@RequestBody @Valid CommentCreationDTO comment) {
    	if(comment.getCommentorId() != ((UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()) {
    		return ResponseEntity.status(403).body(null);
    	}
        return ResponseEntity.ok(
        		commentsMapper.toDTO(commentService.saveComment(commentMapper.toEntity(comment)))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
    	Comment existingComment = commentService.findCommentById(id).orElse(null);
    	if(existingComment==null) {
    		return ResponseEntity.notFound().build();
    	}
    	if(existingComment.getCommentorId() != ((UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()) {
    		return ResponseEntity.status(403).body(null);
    	}
    	commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Comment comment) {
    	Comment existingComment = commentService.findCommentById(id).orElse(null);
    	if(existingComment==null) {
    		return ResponseEntity.notFound().build();
    	}
    	if(existingComment.getCommentorId() != ((UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()) {
    		return ResponseEntity.status(403).body(null);
    	}            
    	return ResponseEntity.ok(commentService.updateComment(id, comment));
    }
}
