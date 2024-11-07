package com.eheiste.laureatnet.service.Impl;

import com.eheiste.laureatnet.dto.modelview.CommentsDTO;
import com.eheiste.laureatnet.mapper.CommentsMapper;
import com.eheiste.laureatnet.model.Comment;
import com.eheiste.laureatnet.repository.CommentRepository;
import com.eheiste.laureatnet.service.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    private CommentsMapper commentsMapper;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<CommentsDTO> findAllComments() {
        return commentRepository.findAll().stream().map(commentsMapper::toDTO).toList();
    }
    @Override
    public Optional<Comment> findCommentById(Long id) {
        return commentRepository.findById(id);
    }
    @Override
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }
    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
    public Comment updateComment(Long id, Comment newComment) {
        return commentRepository.findById(id)
                .map(comment -> {
                    comment.setRepliesCount(newComment.getRepliesCount());
                    comment.setReplyTo(newComment.getReplyTo());
                    comment.setCreatedAt(newComment.getCreatedAt());
                    comment.setPost(newComment.getPost());
                    comment.setCommentor(newComment.getCommentor());
                    return commentRepository.save(comment);
                })
                .orElseThrow(() -> new RuntimeException("Comment not found with id " + id)); // Custom exception handling recommended
    }

	@Override
	public List<CommentsDTO> findAllCommentsByPostId(Long postId) {
        return commentRepository.findByPost_Id(postId).stream().map(commentsMapper::toDTO).toList();
	}

}
