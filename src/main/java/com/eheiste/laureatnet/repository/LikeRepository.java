package com.eheiste.laureatnet.repository;

import com.eheiste.laureatnet.model.Like;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    @Modifying
    @Query("delete from Like l where l.post.id=?1 and l.liker.id=?2")
    void deleteLikeByPostIdAndLikerId(Long postId, Long likerId);

    @Query("select l from Like l where l.post.id=?1 and l.liker.id=?2")
    Optional<Like> findLikeByPostIdAndLikerId(Long postId, Long likerId);

    List<Like> findLikeByLiker_Id(Long likerId);

}

