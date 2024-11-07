package com.eheiste.laureatnet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InternshipOffer extends Post {

    private String duration;

    private LocalDate startDate;

    private LocalDate endDate;

    private String title;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "entreprise_id")
    private Entreprise entreprise;
}
