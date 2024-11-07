package com.eheiste.laureatnet.model;

import org.hibernate.annotations.Cascade;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScientificArticlePost extends Post {
   
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "scientific_article_id")
    private ScientificArticle article;

}
