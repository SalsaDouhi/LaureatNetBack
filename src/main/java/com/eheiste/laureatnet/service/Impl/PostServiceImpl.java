package com.eheiste.laureatnet.service.Impl;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.eheiste.laureatnet.dto.modelview.UniversalPostDTO;
import com.eheiste.laureatnet.model.Post;
import com.eheiste.laureatnet.repository.PostRepository;
import com.eheiste.laureatnet.service.PostService;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public Optional<Post> findPostById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

	@Override
	public Page<Post> getAllPostsSorted(Long userId, Pageable pageable) {
		// TODO Auto-generated method stub
        return postRepository.findAllSortedByRecommendationGrade(userId, pageable);
	}
}
