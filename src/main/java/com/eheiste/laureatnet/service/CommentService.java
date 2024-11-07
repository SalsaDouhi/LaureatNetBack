package com.eheiste.laureatnet.service;

import com.eheiste.laureatnet.dto.modelview.CommentsDTO;
import com.eheiste.laureatnet.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    public List<CommentsDTO> findAllComments();

    public Optional<Comment> findCommentById(Long id);
    public Comment saveComment(Comment comment);

    public void deleteComment(Long id);
    public Comment updateComment(Long id, Comment newComment);

	public List<CommentsDTO> findAllCommentsByPostId(Long postId);
}
