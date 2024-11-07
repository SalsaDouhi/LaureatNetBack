package com.eheiste.laureatnet.service;

import com.eheiste.laureatnet.model.InternshipOffer;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InternshipOfferService {
    InternshipOffer saveInternshipOffer(InternshipOffer internshipOffer);

    Optional<InternshipOffer> findInternshipOfferById(long id);

    void deleteInternshipOfferById(long id);

    InternshipOffer updateInternshipOffer(InternshipOffer existingInternshipOffer, InternshipOffer updatedInternshipOffer);

    Page<InternshipOffer> getAllInternshipOffers(Pageable pageable);
    
    Page<InternshipOffer> getAllPostsSorted(Long userId, Pageable pageable);

}
