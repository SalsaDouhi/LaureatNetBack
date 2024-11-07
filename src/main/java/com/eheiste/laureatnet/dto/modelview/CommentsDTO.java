package com.eheiste.laureatnet.dto.modelview;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CommentsDTO {
    private long id;
    private String content;
    private long userId;
    private String userFullName;
    private String userUsername;

    private String userProfileImage;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}