package com.eheiste.laureatnet.service;

import com.eheiste.laureatnet.model.JobOffer;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobOfferService {

    Optional<JobOffer> findJobOfferById(long id);

    JobOffer saveJobOffer(JobOffer jobOffer);

    void deleteJobOfferById(long id);
    
    JobOffer updateJobOffer(JobOffer existingJobOffer, JobOffer updatedJobOffer);

    Page<JobOffer> getAllJobOffers(Pageable pageable);
    
    Page<JobOffer> getAllPostsSorted(Long userId, Pageable pageable);
}
