package com.eheiste.laureatnet.controller;

import com.eheiste.laureatnet.dto.modelview.ScientificArticleShowDTO;
import com.eheiste.laureatnet.mapper.ScientificArticleShowMapper;
import com.eheiste.laureatnet.model.ScientificArticle;
import com.eheiste.laureatnet.service.ScientificArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/scientificarticle")
public class ScientificArticleController {
    @Autowired
    private ScientificArticleService scientificArticleService;
    @Autowired
    private ScientificArticleShowMapper scientificArticleShowMapper;
    
    @GetMapping
    public Collection<ScientificArticle> getAllScientificArticles() {
        return scientificArticleService.getAllScientificArticles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScientificArticle> getScientificArticle(@PathVariable long id) {
        ScientificArticle scientificArticle = scientificArticleService.findScientificArticleById(id);
        if (scientificArticle != null) {
            return ResponseEntity.ok(scientificArticle);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/by-author/{authorId}")
    public Collection<ScientificArticleShowDTO> getArticlesByAuthorId(@PathVariable Long authorId) {
        return scientificArticleService.getArticlesByAuthorId(authorId).stream()
        		.map(article -> scientificArticleShowMapper.EntityToDTO(article))
        		.toList();
    }

    @DeleteMapping("/{id}")
    public void deleteScientificArticle(@PathVariable long id) {
        scientificArticleService.deleteScientificArticleById(id);
    }

    @PostMapping
    public ResponseEntity<ScientificArticle> createScientificArticle(@RequestBody ScientificArticle scientificArticle) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scientificArticleService.saveScientificArticle(scientificArticle));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScientificArticle> updateScientificArticle(@PathVariable long id, @RequestBody ScientificArticle scientificArticle) {
        ScientificArticle existingScientificArticle = scientificArticleService.findScientificArticleById(id);
        if (existingScientificArticle != null) {
            existingScientificArticle.setTitle(scientificArticle.getTitle());
            existingScientificArticle.setDatePublished(scientificArticle.getDatePublished());
            existingScientificArticle.setPath(scientificArticle.getPath());
            existingScientificArticle.setAuthors(scientificArticle.getAuthors());
            //existingScientificArticle.setPostList(scientificArticle.getPostList());
            scientificArticleService.saveScientificArticle(existingScientificArticle);
            return ResponseEntity.ok(existingScientificArticle);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
