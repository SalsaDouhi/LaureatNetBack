package com.eheiste.laureatnet.repository;

import com.eheiste.laureatnet.model.Post;
import com.eheiste.laureatnet.model.ScientificArticlePost;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScientificArticlePostRepository extends JpaRepository<ScientificArticlePost, Long> {
    @Query("SELECT p " +
            "FROM ScientificArticlePost p " +
            "LEFT JOIN Subscription s ON p.poster.id = s.subscribedTo.id AND s.subscriber.id = :userId " +
            "ORDER BY " +
            "CASE WHEN s.subscriber.id IS NOT NULL THEN 1 ELSE 0 END DESC, " +
            "FUNCTION('DATE', p.createdAt) DESC, " +
            "CASE WHEN p.poster.id = :userId THEN 1 ELSE 0 END ASC")
    Page<ScientificArticlePost> findAllSortedByRecommendationGrade(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT COUNT(s) FROM ScientificArticlePost s WHERE YEAR(s.createdAt) = YEAR(CURRENT_DATE)")
    int countPostsThisYear();

    @Query("SELECT COUNT(s) FROM ScientificArticlePost s WHERE MONTH(s.createdAt) = MONTH(CURRENT_DATE) AND YEAR(s.createdAt) = YEAR(CURRENT_DATE)")
    int countPostsThisMonth();

    @Query("SELECT COUNT(s) FROM ScientificArticlePost s WHERE MONTH(s.createdAt) = ?1 AND YEAR(s.createdAt) = YEAR(CURRENT_DATE)")
    int countPostsByMonth(int month);

    @Query("SELECT COUNT(s) FROM ScientificArticlePost s")
    int countTotal();
}
