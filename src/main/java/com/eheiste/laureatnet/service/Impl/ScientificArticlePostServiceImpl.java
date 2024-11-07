package com.eheiste.laureatnet.service.Impl;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.eheiste.laureatnet.dto.viewmodel.ExistingArticlePostCreationDTO;
import com.eheiste.laureatnet.dto.viewmodel.NewArticlePostCreationDTO;
import com.eheiste.laureatnet.model.Post;
import com.eheiste.laureatnet.model.ScientificArticlePost;
import com.eheiste.laureatnet.repository.ScientificArticlePostRepository;
import com.eheiste.laureatnet.service.ScientificArticlePostService;

import jakarta.validation.Valid;

@Service
public class ScientificArticlePostServiceImpl implements ScientificArticlePostService {

    @Autowired
    private ScientificArticlePostRepository scientificArticlePostRepository;

    @Override
    public Page<ScientificArticlePost> getAllScientificArticlePosts(Pageable pageable) {
        return scientificArticlePostRepository.findAll(pageable);
    }

    @Override
    public Page<ScientificArticlePost> getAllPostsSorted(Long userId, Pageable pageable) {
        return scientificArticlePostRepository.findAllSortedByRecommendationGrade(userId, pageable);
    }
    @Override
    public Optional<ScientificArticlePost> findScientificArticlePostById(Long id) {
        return scientificArticlePostRepository.findById(id);
    }

    @Override
    public void deleteScientificArticlePostById(Long id) {
        scientificArticlePostRepository.deleteById(id);
    }

    @Override
    public ScientificArticlePost saveScientificArticlePost(ScientificArticlePost scientificArticlePost) {
        return scientificArticlePostRepository.save(scientificArticlePost);
    }

    @Override
    public ScientificArticlePost updateScientificArticlePost(ScientificArticlePost existingScientificArticlePost, ScientificArticlePost updatedScientificArticlePost) {
    	existingScientificArticlePost.setContent(updatedScientificArticlePost.getContent());
    	existingScientificArticlePost.setLikesCount(updatedScientificArticlePost.getLikesCount());
    	existingScientificArticlePost.setCreatedAt(updatedScientificArticlePost.getCreatedAt());
    	existingScientificArticlePost.setModifiedAt(updatedScientificArticlePost.getModifiedAt());
    	existingScientificArticlePost.setPoster(updatedScientificArticlePost.getPoster());
    	existingScientificArticlePost.setAttachmentList(updatedScientificArticlePost.getAttachmentList());
    	existingScientificArticlePost.setTechnologyList(updatedScientificArticlePost.getTechnologyList());
    	existingScientificArticlePost.setLikesList(updatedScientificArticlePost.getLikesList());
    	//existingScientificArticlePost.setCommentList(updatedScientificArticlePost.getCommentList());
    	existingScientificArticlePost.setArticle(updatedScientificArticlePost.getArticle());
    	return scientificArticlePostRepository.save(existingScientificArticlePost);
    }


}
