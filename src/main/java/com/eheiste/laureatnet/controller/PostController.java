package com.eheiste.laureatnet.controller;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eheiste.laureatnet.dto.modelview.UniversalPostDTO;
import com.eheiste.laureatnet.dto.viewmodel.PostCreationDTO;
import com.eheiste.laureatnet.mapper.EntrepriseMapper;
import com.eheiste.laureatnet.mapper.PostCreationMapper;
import com.eheiste.laureatnet.mapper.UniversalPostMapper;
import com.eheiste.laureatnet.model.Attachment;
import com.eheiste.laureatnet.model.Post;
import com.eheiste.laureatnet.model.UserAccount;
import com.eheiste.laureatnet.service.PostService;
import com.eheiste.laureatnet.service.UserAccountService;
import com.eheiste.laureatnet.service.Impl.UserAccountServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private PostCreationMapper postCreationMapper;
    @Autowired
    private UniversalPostMapper postUniversalMapper;
    private int pageSize=10;
    @Autowired
	private UserAccountService userAccountService;
    
    @GetMapping
    public Collection<UniversalPostDTO> getAllPosts(
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) Integer size) {

        int effectivePageSize = (size == null) ? pageSize : size;
        Pageable pageable = PageRequest.of(page, effectivePageSize);

        Page<UniversalPostDTO> posts;
        if (userId == null || userId != ((UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()) {
            posts = postService.getAllPosts(pageable).map(postUniversalMapper::toDTO);
        } else {
            posts = postService.getAllPostsSorted(userId, pageable).map(postUniversalMapper::toDTO);
        }

        return posts.getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable long id) {
        Post post = postService.findPostById(id).orElse(null);
        if (post != null) {
            return ResponseEntity.ok(post);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable long id) {
        Post existingPost = postService.findPostById(id).orElse(null);
        if(existingPost == null || existingPost.getPoster() == null || existingPost.getPoster().getId() != ((UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()) {
        	return ResponseEntity.status(403).build();
        }
        postService.deletePostById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@ModelAttribute @Valid PostCreationDTO postDto, BindingResult bindingResult){
        if(postDto.getPosterId() != ((UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()) {
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

        	
        Post post = postCreationMapper.mapToEntity(postDto);
    	return ResponseEntity.status(HttpStatus.CREATED).body(postService.savePost(post));

        //return ResponseEntity.accepted().body("worked");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable long id, @RequestBody Post post) {
        Post existingPost = postService.findPostById(id).orElse(null);
        if(existingPost == null) {
        	return ResponseEntity.notFound().build();
        }else if(existingPost.getPoster() == null || existingPost.getPoster().getId() != ((UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()) {
        		return ResponseEntity.status(403).body(null);
        }
	    if(post.getContent()!=null) existingPost.setContent(post.getContent());
	    existingPost.setLikesCount(post.getLikesCount());
	    existingPost.setModifiedAt(LocalDateTime.now().withNano(0));
	    if(post.getAttachmentList()!=null) existingPost.setAttachmentList(post.getAttachmentList());
	    if(post.getTechnologyList()!=null) existingPost.setTechnologyList(post.getTechnologyList());
	    if(post.getLikesList()!=null) existingPost.setLikesList(post.getLikesList());
	    postService.savePost(existingPost);
	    return ResponseEntity.ok(existingPost);
    }
}
