package com.eheiste.laureatnet.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "user_account_id")
    @JsonIgnore
    private UserAccount userAccount;

    @ManyToOne
    @JoinColumn(name = "section_type_id")
    @JsonIgnore
    private SectionType sectionType;

    @JsonProperty("userAccountId")
    public Long getUserAccountId() {
        return userAccount != null ? userAccount.getId() : null;
    }

    @JsonProperty("sectionTypeId")
    public Long getSectionTypeId() {
        return sectionType != null ? sectionType.getId() : null;
    }
}
