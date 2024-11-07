package com.eheiste.laureatnet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean acceptMsgs = true;

    private boolean privateMsgs = true;
    private boolean administrativePosts = true;
    private boolean connectionRequest = true;

    @OneToOne
    @JoinColumn(name = "user_account_id") // this tells spring to create the foreign key in this table
    @JsonIgnore
    private UserAccount userAccount;

    @JsonProperty("userAccountId")
    public Long getUserAccountId() {
        return userAccount != null ? userAccount.getId() : null;
    }
}
