package com.eheiste.laureatnet.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eheiste.laureatnet.dto.modelview.UniversalPostDTO;
import com.eheiste.laureatnet.model.Post;

public interface PostService {

    Page<Post> getAllPosts(Pageable pageable);
    
    Page<Post> getAllPostsSorted(Long userId, Pageable pageable);
    
    Optional<Post> findPostById(Long id);

    void deletePostById(Long id);

    Post savePost(Post post);

}
