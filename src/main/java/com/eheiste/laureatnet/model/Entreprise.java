package com.eheiste.laureatnet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Table(name = "entreprise")
public class Entreprise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String logo;
   // private MultipartFile logoFile;
   private double localisationX;
    private double localisationY;
    private String  tel;
    private String email;
    private String website;


    /*@JsonIgnore
    @OneToMany(mappedBy = "entreprise", orphanRemoval = true, cascade = CascadeType.ALL)
    private Collection<JobOffer> jobOffers;

    @JsonIgnore
    @OneToMany(mappedBy = "entreprise", orphanRemoval = true, cascade = CascadeType.ALL)
    private Collection<InternshipOffer> internshipOffers;*/

    @JsonIgnore
    @OneToMany(mappedBy = "entreprise", cascade = CascadeType.ALL)
    private List<InternshipOffer> internshipOfferList;

    @JsonIgnore
    @OneToMany(mappedBy = "entreprise", cascade = CascadeType.ALL)
    private List<JobOffer> jobOfferList;
}