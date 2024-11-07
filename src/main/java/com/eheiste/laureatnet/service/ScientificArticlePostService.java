package com.eheiste.laureatnet.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eheiste.laureatnet.dto.viewmodel.ExistingArticlePostCreationDTO;
import com.eheiste.laureatnet.dto.viewmodel.NewArticlePostCreationDTO;
import com.eheiste.laureatnet.model.ScientificArticlePost;

import jakarta.validation.Valid;

public interface ScientificArticlePostService {

    Page<ScientificArticlePost> getAllScientificArticlePosts(Pageable pageable);
    
    Page<ScientificArticlePost> getAllPostsSorted(Long userId, Pageable pageable);
    
    Optional <ScientificArticlePost> findScientificArticlePostById(Long id);

    void deleteScientificArticlePostById(Long id);

    ScientificArticlePost saveScientificArticlePost(ScientificArticlePost scientificArticlePost);


	ScientificArticlePost updateScientificArticlePost(ScientificArticlePost existingScientificArticlePost,
			ScientificArticlePost updatedScientificArticlePost);


}
