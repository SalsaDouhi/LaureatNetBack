package com.eheiste.laureatnet.service.Impl;

import com.eheiste.laureatnet.model.JobOffer;
import com.eheiste.laureatnet.model.Post;
import com.eheiste.laureatnet.model.UserAccount;
import com.eheiste.laureatnet.repository.JobOfferRepository;
import com.eheiste.laureatnet.service.JobOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Service
public class JobOfferServiceImpl implements JobOfferService {

    @Autowired
    private JobOfferRepository jobOfferRepository;

    @Override
    public Page<JobOffer> getAllJobOffers(Pageable pageable) {
        return jobOfferRepository.findAll(pageable);
    }

    @Override
    public Page<JobOffer> getAllPostsSorted(Long userId, Pageable pageable) {
        return jobOfferRepository.findAllSortedByRecommendationGrade(userId, pageable);
    }
    @Override
    public Optional<JobOffer> findJobOfferById(long id) {
        return jobOfferRepository.findById(id);
    }

    @Override
    public JobOffer saveJobOffer(JobOffer jobOffer) {
        return jobOfferRepository.save(jobOffer);
    }

    @Override
    public void deleteJobOfferById(long id) {
        jobOfferRepository.deleteById(id);
    }
    @Override
    public JobOffer updateJobOffer(JobOffer existingJobOffer, JobOffer updatedJobOffer) {
    UserAccount posterJobOffer = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    // Vérifier si l'utilisateur connecté est le créateur de l'offre de stage
    if (posterJobOffer.getId() == existingJobOffer.getPoster().getId()) {
        // Mise à jour de toutes les propriétés de JobOffer
        existingJobOffer.setContent(updatedJobOffer.getContent());
        existingJobOffer.setLikesCount(updatedJobOffer.getLikesCount());
        existingJobOffer.setModifiedAt(LocalDateTime.now().withNano(0));
        existingJobOffer.setAttachmentList(updatedJobOffer.getAttachmentList());
        existingJobOffer.setTechnologyList(updatedJobOffer.getTechnologyList());
        existingJobOffer.setLikesList(updatedJobOffer.getLikesList());
        // Enregistrer l'offre de emplois mise à jour
        return jobOfferRepository.save(existingJobOffer);
    } else {
        throw new IllegalArgumentException("Only the creator can update this JobOffer ");
    }
}
}

