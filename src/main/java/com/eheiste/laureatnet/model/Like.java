package com.eheiste.laureatnet.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "likes") //the word like is reserved in sql
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "liker_id")
    private UserAccount liker;

    @JsonBackReference(value = "post")
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "post_id")
    private Post post;

    @JsonProperty("likerId")
    public Long getLikerId() {
        return liker != null ? liker.getId() : null;
    }
    @JsonProperty("postId")
    public Long getPostId() {
        return this.post.getId();
    }
}
