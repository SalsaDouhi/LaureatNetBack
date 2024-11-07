package com.eheiste.laureatnet.mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eheiste.laureatnet.dto.viewmodel.ExistingArticlePostCreationDTO;
import com.eheiste.laureatnet.dto.viewmodel.NewArticlePostCreationDTO;
import com.eheiste.laureatnet.dto.viewmodel.PostCreationDTO;
import com.eheiste.laureatnet.model.Attachment;
import com.eheiste.laureatnet.model.Post;
import com.eheiste.laureatnet.model.ScientificArticle;
import com.eheiste.laureatnet.model.ScientificArticlePost;
import com.eheiste.laureatnet.model.UserAccount;
import com.eheiste.laureatnet.service.FileStorageService;
import com.eheiste.laureatnet.service.ScientificArticleService;
import com.eheiste.laureatnet.service.UserAccountService;

@Component
public class ScientificArticlePostCreationMapper {
	@Autowired
	private FileStorageService fileStorageService;
	@Autowired
	private ScientificArticleService articleService;
	@Autowired
	private UserAccountService userService;

    public ScientificArticlePost mapExistingToEntity(ExistingArticlePostCreationDTO dto) {
    	ScientificArticlePost post = new ScientificArticlePost();
    	post.setPoster(userService.loadById(dto.getPosterId()).get());
        post.setArticle(articleService.findScientificArticleById(dto.getArticleId()));
        
        post.setContent(dto.getContent());
        
    	post.setCreatedAt(LocalDateTime.now());
    	post.setModifiedAt(LocalDateTime.now());
    	
        return post;
    }
    public ScientificArticlePost mapNewToEntity(NewArticlePostCreationDTO dto) {
    	ScientificArticlePost post = new ScientificArticlePost();
    	post.setPoster(userService.loadById(dto.getPosterId()).get());
    	
        post.setContent(dto.getContent());
        ScientificArticle article = new ScientificArticle();
        article.setTitle(dto.getTitle());
        article.setAuthors(dto.getAuthors());
        article.setDatePublished(dto.getDatePublished());
        //traitement de upload fichier

	    Map.Entry<String,String> articlePathAndType = fileStorageService.storeFile(dto.getArticle()).entrySet().iterator().next();
	    
	    article.setPath(articlePathAndType.getKey());
	    post.setArticle(article);
        

    	post.setCreatedAt(LocalDateTime.now());
    	post.setModifiedAt(LocalDateTime.now());
        return post;
    }
}
