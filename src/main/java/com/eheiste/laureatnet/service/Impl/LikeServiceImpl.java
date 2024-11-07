package com.eheiste.laureatnet.service.Impl;

import com.eheiste.laureatnet.model.Like;
import com.eheiste.laureatnet.repository.LikeRepository;
import com.eheiste.laureatnet.service.LikeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
import java.util.Optional;

    @Service
    public class LikeServiceImpl implements LikeService {

        private final LikeRepository likeRepository;

        public LikeServiceImpl(LikeRepository likeRepository) {
            this.likeRepository = likeRepository;
        }
        @Transactional
        @Override
        public Like saveLike(Like like) {
            return likeRepository.save(like);
        }
        @Transactional(readOnly = true)
        @Override
        public Optional<Like> getLikeById(Long id) {
            return likeRepository.findById(id);
        }

        @Override
        @Transactional
        public Collection<Like> getAllLikes() {
            return likeRepository.findAll();
        }

        @Override
        public void deleteLike(Long id) {
            likeRepository.deleteById(id);
        }
        @Override
        @Transactional
        public void deleteLikeByPostIdAndLikerId(Long postId, Long likerId) {
            likeRepository.deleteLikeByPostIdAndLikerId(postId, likerId);
        }
		@Override
		public Optional<Like> getLikeByPostIdAndLikerId(Long postId, Long likerId) {
			return likeRepository.findLikeByPostIdAndLikerId(postId, likerId);
		}
    }


