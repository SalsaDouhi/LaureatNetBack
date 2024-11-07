package com.eheiste.laureatnet.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String content;

    private int likesCount;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
    
    //@JsonManagedReference(value = "poster")
    @JsonIgnore
    @ManyToOne
    private UserAccount poster;
    
    @JsonIgnore
    @OneToMany(mappedBy = "post", orphanRemoval=true, cascade = CascadeType.ALL)
    private Collection<Attachment> attachmentList;
    
    
    //@JsonManagedReference(value = "post")
    @ManyToMany()
    @JoinTable(
      name = "post_technology", 
      joinColumns = @JoinColumn(name = "post_id"),
      inverseJoinColumns = @JoinColumn(name = "technology_id"))
    private Collection<Technology> technologyList;
 
    @JsonIgnore
    @OneToMany(mappedBy = "post", orphanRemoval=true, cascade = CascadeType.ALL)
    private Collection<Like> likesList;

    @OneToMany(mappedBy = "post", orphanRemoval=true, cascade = CascadeType.ALL)
    private Collection<Comment> commentList;
}
