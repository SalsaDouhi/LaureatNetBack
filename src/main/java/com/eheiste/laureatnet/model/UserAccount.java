package com.eheiste.laureatnet.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;
    private String password;
    @Column(columnDefinition = "Bit(1) default 1")
    private boolean isEnabled;
    @Column(columnDefinition = "Bit(1) default 0")
    private boolean isLocked;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "poster", cascade = CascadeType.ALL)
    private List<Post> posts;

    @OneToOne(mappedBy = "userAccount", cascade = CascadeType.ALL)
    private UserProfile userProfile;

    @OneToOne(mappedBy = "userAccount", cascade = CascadeType.ALL)
    private UserSetting userSettings;

    @ManyToOne
    @JoinColumn(name = "account_type_id")
    private AccountType accountType;

    // Section Item
    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL)
    private List<SectionItem> sectionItems;

    // RequestDoctorate
    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
    private List<RequestDoctorate> requestDoctorates;

    // Block
    @OneToMany(mappedBy = "blocker", cascade = CascadeType.ALL)
    // blocker refers to the property name in the Block class
    private List<Block> blocksInitiated;

    @OneToMany(mappedBy = "blocked", cascade = CascadeType.ALL)
    private List<Block> blocksReceived;

    // Subscription
    @OneToMany(mappedBy = "subscriber", cascade = CascadeType.ALL)
    private List<Subscription> subscriptionsMade;

    @OneToMany(mappedBy = "subscribedTo", cascade = CascadeType.ALL)
    private List<Subscription> subscriptionsReceived;

    // Connection
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<Connection> connectionsMade;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private List<Connection> connectionsReceived;

    // Report
    @OneToMany(mappedBy = "reporter", cascade = CascadeType.ALL)
    private List<Report> reportsMade;

    @OneToMany(mappedBy = "reported", cascade = CascadeType.ALL)
    private List<Report> reportsReceived;
    // Report
    @OneToMany(mappedBy = "user1", cascade = CascadeType.ALL)
    private List<Conversation> user1;

    @OneToMany(mappedBy = "user2", cascade = CascadeType.ALL)
    private List<Conversation> user2;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Collection<Notification> notificationList;

//    @JsonBackReference(value = "poster")
    // @OneToMany(mappedBy = "poster")
    //  private List<Post> postList;

    @OneToMany(mappedBy = "publisher", orphanRemoval = true, cascade = CascadeType.ALL)
    private Collection<ScientificArticle> articlesPublished;

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        // todo: get roles from 'this.accountType'
//        return List.of(new SimpleGrantedAuthority("ADMIN"));
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(accountType.getRoles().split(","))
                .map(String::trim)
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

    // security: UserDetails getters
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // todo: fix is account non locked
//        return !this.isLocked;
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // todo: fix is account enabled
//        return isEnabled;
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        return result;
    }

}
