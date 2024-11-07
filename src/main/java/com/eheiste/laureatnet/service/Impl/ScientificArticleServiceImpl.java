package com.eheiste.laureatnet.service.Impl;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eheiste.laureatnet.model.ScientificArticle;
import com.eheiste.laureatnet.repository.ScientificArticleRepository;
import com.eheiste.laureatnet.service.ScientificArticleService;

@Service
public class ScientificArticleServiceImpl implements ScientificArticleService {

    @Autowired
    private ScientificArticleRepository scientificArticleRepository;

    @Override
    public Collection<ScientificArticle> getAllScientificArticles() {
        return scientificArticleRepository.findAll();
    }

    @Override
    public ScientificArticle findScientificArticleById(Long id) {
        Optional<ScientificArticle> scientificArticleOptional = scientificArticleRepository.findById(id);
        return scientificArticleOptional.orElse(null);
    }

    @Override
    public void deleteScientificArticleById(Long id) {
        scientificArticleRepository.deleteById(id);
    }

    @Override
    public ScientificArticle saveScientificArticle(ScientificArticle scientificArticle) {
        return scientificArticleRepository.save(scientificArticle);
    }

	@Override
	public Collection<ScientificArticle> getArticlesByAuthorId(Long id) {	
        return scientificArticleRepository.findArticlesByPublisherId(id);
	}
}
