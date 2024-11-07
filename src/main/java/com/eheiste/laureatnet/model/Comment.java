package com.eheiste.laureatnet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private int repliesCount;
    private Long replyTo;
    private LocalDateTime createdAt;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "commentor_id")
    private UserAccount commentor;

    @JsonProperty("postId")
    public Long getPostId() {
        return this.post.getId();
    }

    @JsonProperty("posterId")
    public Long getPosterId(){
        return post != null ? post.getPoster().getId() : null;
    }
    @JsonProperty("posterFullName")
    public String getPosterFullName(){
        return post != null ? post.getPoster().getUserProfile().getFirstName() + " " + post.getPoster().getUserProfile().getLastName() : null;
    }

    @JsonProperty("commentorId")
    public Long getCommentorId() {
        return commentor != null ? commentor.getId() : null;
    }

    @JsonProperty("commentorFullName")
    public String getCommentorFullName() {
        return commentor != null ? commentor.getUserProfile().getFirstName() + " " + commentor.getUserProfile().getLastName() : null;
    }

}
