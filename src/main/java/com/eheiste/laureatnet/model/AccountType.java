package com.eheiste.laureatnet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountType {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    private String roles;

    @JsonIgnore
    @OneToMany(mappedBy = "accountType", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserAccount> userAccounts = new HashSet<>();
}
