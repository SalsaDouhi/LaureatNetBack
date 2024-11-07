package com.eheiste.laureatnet.repository;

import com.eheiste.laureatnet.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByCommentor_IdOrderByCreatedAt(Long commentorId);
    List<Comment> findByCommentor_IdOrderByCreatedAtDesc(Long commentorId);
    List<Comment> findByPost_Id(Long postId);
}
