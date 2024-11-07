package com.eheiste.laureatnet.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eheiste.laureatnet.model.ScientificArticle;

@Repository
public interface ScientificArticleRepository extends JpaRepository<ScientificArticle, Long> {
	Collection<ScientificArticle> findArticlesByPublisherId(Long id);
}
