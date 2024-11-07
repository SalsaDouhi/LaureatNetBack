package com.eheiste.laureatnet.controller;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eheiste.laureatnet.dto.modelview.ScientificArticlePostShowDTO;
import com.eheiste.laureatnet.dto.modelview.UniversalPostDTO;
import com.eheiste.laureatnet.dto.viewmodel.ExistingArticlePostCreationDTO;
import com.eheiste.laureatnet.dto.viewmodel.NewArticlePostCreationDTO;
import com.eheiste.laureatnet.mapper.ScientificArticlePostCreationMapper;
import com.eheiste.laureatnet.mapper.ScientificArticlePostShowMapper;
import com.eheiste.laureatnet.mapper.UniversalPostMapper;
import com.eheiste.laureatnet.model.JobOffer;
import com.eheiste.laureatnet.model.Post;
import com.eheiste.laureatnet.model.ScientificArticlePost;
import com.eheiste.laureatnet.model.UserAccount;
import com.eheiste.laureatnet.service.ScientificArticlePostService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/scientific-article-posts")
public class ScientificArticlePostController {

    @Autowired
    private ScientificArticlePostService scientificArticlePostService;
    @Autowired
	private ScientificArticlePostCreationMapper scientificArticlePostCreationMapper;

    @Autowired
	private UniversalPostMapper universalPostMapper;

    @GetMapping
    public ResponseEntity<Collection<UniversalPostDTO>> getAllScientificArticlePosts(
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ScientificArticlePost> scientificArticles;

        if (userId == null || userId != ((UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()) {
            scientificArticles = scientificArticlePostService.getAllScientificArticlePosts(pageable);
        } else {
            scientificArticles = scientificArticlePostService.getAllPostsSorted(userId, pageable);
        }

        return ResponseEntity.status(HttpStatus.OK).body(
            scientificArticles.stream()
            .map(universalPostMapper::toDTO)
            .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScientificArticlePost> getScientificArticlePostById(@PathVariable long id) {
        ScientificArticlePost scientificArticlePost = scientificArticlePostService.findScientificArticlePostById(id).orElse(null);
        if (scientificArticlePost != null) {
            return ResponseEntity.ok(scientificArticlePost);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScientificArticlePostById(@PathVariable long id) {
    	ScientificArticlePost existingPost = scientificArticlePostService.findScientificArticlePostById(id).orElse(null);
        if(existingPost == null || existingPost.getPoster() == null || existingPost.getPoster().getId() != ((UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()) {
        	return ResponseEntity.status(403).build();
        }
        scientificArticlePostService.deleteScientificArticlePostById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<UniversalPostDTO> createScientificArticlePost(@ModelAttribute @Valid ExistingArticlePostCreationDTO postDto, BindingResult bindingResult) {
        if((postDto.getPosterId() != ((UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId())) {
            System.out.println("not your account");
            return ResponseEntity.status(403).body(null);
        }
        if((SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().contentEquals("ROLE_DOC"))==false)
           ) {
        System.out.println("dont have the permissions");
        SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .forEach(authority -> {System.out.println( authority.getAuthority());
                System.out.println("the equality check is : "+authority.getAuthority().contentEquals("ROLE_DOC"));});
            return ResponseEntity.status(403).body(null);
        }

        if (bindingResult.hasFieldErrors()) {
        	List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors){
                System.out.println("Field " + error.getField() + ": " + error.getDefaultMessage());
            }
            System.out.println("-----------");
            return ResponseEntity.badRequest().body(null);
        }
        ScientificArticlePost post = scientificArticlePostCreationMapper.mapExistingToEntity(postDto);

        
        return ResponseEntity.status(HttpStatus.CREATED).body(
        		universalPostMapper.toDTO(scientificArticlePostService.saveScientificArticlePost(post))
        );
    }

    @PostMapping("/new-article")
    public ResponseEntity<UniversalPostDTO> createScientificArticlePostNewArticle(@ModelAttribute @Valid NewArticlePostCreationDTO postDto, BindingResult bindingResult) {
        if((postDto.getPosterId() != ((UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId())) {
            System.out.println("not your account");
            return ResponseEntity.status(403).body(null);
        }
        if((SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().contentEquals("ROLE_DOC"))==false)
           ) {
        System.out.println("dont have the permissions");
        SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .forEach(authority -> {System.out.println( authority.getAuthority());
                System.out.println("the equality check is : "+authority.getAuthority().contentEquals("ROLE_DOC"));});
            return ResponseEntity.status(403).body(null);
        }

        if (bindingResult.hasFieldErrors()) {
        	List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors){
                System.out.println("Field " + error.getField() + ": " + error.getDefaultMessage());
            }
            System.out.println("-----------");
            return ResponseEntity.badRequest().body(null);        
        }
        System.out.println(postDto.getDatePublished());
        ScientificArticlePost post = scientificArticlePostCreationMapper.mapNewToEntity(postDto);

        
        return ResponseEntity.status(HttpStatus.CREATED).body(
        		universalPostMapper.toDTO(scientificArticlePostService.saveScientificArticlePost(post))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScientificArticlePost> updateScientificArticlePost(@PathVariable long id, @RequestBody ScientificArticlePost scientificArticlePost) {
    	ScientificArticlePost existingPost = scientificArticlePostService.findScientificArticlePostById(id).orElse(null);
        if(existingPost == null) {
        	return ResponseEntity.notFound().build();
        }else if(existingPost.getPoster() == null || existingPost.getPoster().getId() != ((UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()) {
        	return ResponseEntity.status(403).body(null);
        }

        ScientificArticlePost updatedScientificArticlePost = scientificArticlePostService.updateScientificArticlePost(existingPost, scientificArticlePost);
        return ResponseEntity.ok(updatedScientificArticlePost);
    }
}
