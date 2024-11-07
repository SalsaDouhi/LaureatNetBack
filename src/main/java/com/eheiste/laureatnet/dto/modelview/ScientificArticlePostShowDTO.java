package com.eheiste.laureatnet.dto.modelview;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ScientificArticlePostShowDTO {
    private Long id;

    private String title;

    private LocalDate datePublished;

    private String path;
    
    private String content;
    
    private int likesCount;

    private LocalDateTime postCreatedAt;

    private LocalDateTime postModifiedAt;

    private String authors;

    private Long publisherId;
    
    private String publisherName;
   
}
