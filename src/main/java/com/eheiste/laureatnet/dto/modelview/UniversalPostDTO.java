package com.eheiste.laureatnet.dto.modelview;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class UniversalPostDTO {
    // Common fields
    private long id;
    private String content;
    private int likesCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private long userId;
    private String userUsername;
    private String userFullName;
    private String userProfileImage;

    private String postType;

    // Fields specific to InternshipOffer
    private String internshipDuration;
    private LocalDate internshipStartDate;
    private LocalDate internshipEndDate;
    private String internshipTitle;
    
    private String entrepriseImage;
    private String entrepriseName;
    private long entrepriseId;

    // Fields specific to JobOffer
    private LocalDate jobStartDate;
    private LocalDate jobEndDate;
    private String jobTitle;

    // Fields specific to ScientificArticlePost
    private long scientificArticleId;
    private String scientificArticleTitle;
    private LocalDate scientificArticleDatePublished;
    private String scientificArticlePath;
    private String scientificArticleAuthors;
    private Long scientificArticlePublisherId;
    private String scientificArticlePublisherName;

    // Comments
    private List<CommentsDTO> comments;

    // Technologies map
    private Map<Long, String> technologies;

    // Attachments
    private List<String> attachments;

    // Likers' names
    private List<LikeShowDTO> likes;
}