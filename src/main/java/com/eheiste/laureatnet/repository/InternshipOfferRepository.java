package com.eheiste.laureatnet.repository;

import com.eheiste.laureatnet.model.InternshipOffer;
import com.eheiste.laureatnet.model.JobOffer;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InternshipOfferRepository extends JpaRepository<InternshipOffer, Long> {
    @Query("SELECT i " +
  	       "FROM InternshipOffer i " +
  	       "LEFT JOIN Subscription s ON i.poster.id = s.subscribedTo.id AND s.subscriber.id = :userId " +
  	       "ORDER BY " +
  	       "CASE WHEN s.subscriber.id IS NOT NULL THEN 1 ELSE 0 END DESC, " +
  	       "FUNCTION('DATE', i.createdAt) DESC, " +
  	       "CASE WHEN i.poster.id = :userId THEN 1 ELSE 0 END ASC")
    Page<InternshipOffer> findAllSortedByRecommendationGrade(@Param("userId") Long userId, Pageable pageable);

	@Query("SELECT COUNT(i) FROM InternshipOffer i WHERE YEAR(i.createdAt) = YEAR(CURRENT_DATE)")
	int countPostsThisYear();

	@Query("SELECT COUNT(i) FROM InternshipOffer i WHERE MONTH(i.createdAt) = MONTH(CURRENT_DATE) AND YEAR(i.createdAt) = YEAR(CURRENT_DATE)")
	int countPostsThisMonth();

	@Query("SELECT COUNT(i) FROM InternshipOffer i WHERE MONTH(i.createdAt) = ?1 AND YEAR(i.createdAt) = YEAR(CURRENT_DATE)")
	int countPostsByMonth(int month);

	@Query("SELECT COUNT(i) FROM InternshipOffer i")
	int countTotal();
}
