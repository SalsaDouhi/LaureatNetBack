package com.eheiste.laureatnet.service;

import java.util.Collection;

import com.eheiste.laureatnet.model.ScientificArticle;

public interface ScientificArticleService {

    Collection<ScientificArticle> getAllScientificArticles();
    
    Collection<ScientificArticle> getArticlesByAuthorId(Long id);
    
    ScientificArticle findScientificArticleById(Long id);

    void deleteScientificArticleById(Long id);

    ScientificArticle saveScientificArticle(ScientificArticle scientificArticle);
}
