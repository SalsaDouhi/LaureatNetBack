package com.eheiste.laureatnet.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eheiste.laureatnet.dto.modelview.UniversalPostDTO;
import com.eheiste.laureatnet.model.Post;

import java.util.Collection;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByPosterIdOrderByCreatedAtDesc(Long posterId, Pageable pageable);


    @Query("SELECT p " +
            "FROM Post p " +
            "LEFT JOIN Subscription s ON p.poster.id = s.subscribedTo.id AND s.subscriber.id = :userId " +
            "ORDER BY " +
            "CASE WHEN s.subscriber.id IS NOT NULL THEN 1 ELSE 0 END DESC, " +
            "FUNCTION('DATE', p.createdAt) DESC, " +
            "CASE WHEN p.poster.id = :userId THEN 1 ELSE 0 END ASC")
    Page<Post> findAllSortedByRecommendationGrade(@Param("userId") Long userId, Pageable pageable);


    List<Post> findByPosterIdOrderByCreatedAtDesc(Long id);

    @Query("SELECT COUNT(p) FROM Post p WHERE YEAR(p.createdAt) = YEAR(CURRENT_DATE)")
    int countPostsThisYear();

    @Query("SELECT COUNT(p) FROM Post p WHERE MONTH(p.createdAt) = MONTH(CURRENT_DATE) AND YEAR(p.createdAt) = YEAR(CURRENT_DATE)")
    int countPostsThisMonth();

    @Query("SELECT COUNT(p) FROM Post p WHERE MONTH(p.createdAt) = ?1 AND YEAR(p.createdAt) = YEAR(CURRENT_DATE)")
    int countPostsByMonth(int month);

    @Query("SELECT COUNT(p) FROM Post p")
    int countTotal();
}
