package com.eheiste.laureatnet.dto.modelview;

import java.time.LocalDate;
import java.util.Collection;

import com.eheiste.laureatnet.model.ScientificArticlePost;
import com.eheiste.laureatnet.model.UserAccount;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
public class ScientificArticleShowDTO {
	
    private Long id;

    private String title;

    private LocalDate datePublished;

    private String path;

    private String authors;

    private Long publisherId;
    
    private String publisherName;
   
}
