package com.eheiste.laureatnet.controller;
import com.eheiste.laureatnet.dto.modelview.LikeShowDTO;
import com.eheiste.laureatnet.mapper.LikeShowMapper;
import com.eheiste.laureatnet.model.Like;
import com.eheiste.laureatnet.model.Post;
import com.eheiste.laureatnet.model.UserAccount;
import com.eheiste.laureatnet.service.LikeService;
import com.eheiste.laureatnet.service.UserAccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/likes")
public class LikeController {

	@Autowired
    private LikeService likeService;	
	@Autowired
    private UserAccountService userService;
	@Autowired
    private LikeShowMapper likeShowMapper;


    @ResponseBody
    @PostMapping
    public ResponseEntity<LikeShowDTO> createLike(@RequestParam Long postId, @RequestParam Long likerId) {
    	if(likerId != ((UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()) {
    		return ResponseEntity.status(403).body(null);
    	}
    	if(likeService.getLikeByPostIdAndLikerId(postId, likerId).orElse(null) != null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	
    	Like like = new Like();
    	UserAccount liker = userService.loadById(likerId).orElseThrow(() -> new RuntimeException("ha"));
    	Post post = new Post();
    	
    	liker.setId(likerId);
    	post.setId(postId);
    	
    	like.setLiker(liker);
    	like.setPost(post);
    	
        Like savedLike = likeService.saveLike(like);
        
        return new ResponseEntity<>( likeShowMapper.toDTO(savedLike) ,HttpStatus.CREATED);
        //return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        //return new ResponseEntity<>("{well:1}" ,HttpStatus.CREATED);
		//return new ResponseEntity<>(postId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Like> getLikeById(@PathVariable Long id) {
        return likeService.getLikeById(id)
                .map(like -> new ResponseEntity<>(like, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<Collection<Like>> getAllLikes() {
        Collection<Like> likes = likeService.getAllLikes();
        return new ResponseEntity<>(likes, HttpStatus.OK);
    }


    @DeleteMapping
    public ResponseEntity<Void> deleteLike(@RequestParam Long postId, @RequestParam Long likerId) {
    	if(likerId != ((UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()){
    		return ResponseEntity.status(403).build();
    	}
    	likeService.deleteLikeByPostIdAndLikerId(postId, likerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLike(@PathVariable Long id) {
    	Like like = likeService.getLikeById(id).orElse(null);
    	if(like==null ||like.getLiker()==null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	if(like.getLiker().getId() != ((UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()) {
            return ResponseEntity.status(403).build();
    	}
    	likeService.deleteLike(id);
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
