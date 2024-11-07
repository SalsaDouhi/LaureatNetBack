package com.eheiste.laureatnet.service;

import com.eheiste.laureatnet.model.Like;

import java.util.Collection;
import java.util.Optional;

public interface LikeService {
    public Like saveLike(Like like);
    public Optional<Like> getLikeById(Long id);
    public Collection<Like> getAllLikes();
    public void deleteLike(Long id);
	void deleteLikeByPostIdAndLikerId(Long postId, Long likerId);
	Optional<Like> getLikeByPostIdAndLikerId(Long postId, Long likerId);
}
