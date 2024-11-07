package com.eheiste.laureatnet.dto;

import com.eheiste.laureatnet.dto.modelview.UniversalPostDTO;

import com.eheiste.laureatnet.model.Comment;
import com.eheiste.laureatnet.model.Like;
import com.eheiste.laureatnet.model.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {
    private Long userId;
    private String email;
    private UserProfile userProfile;
    private Boolean isOwner; // is the user the owner of the profile

    private List<UniversalPostDTO> posts;
    private List<ProfileDTO> friends;
    private List<Comment> comments;
    private List<Like> likes;
    private List<SectionDTO> sections;
}
