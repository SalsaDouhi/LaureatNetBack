package com.eheiste.laureatnet.service.Impl;

import com.eheiste.laureatnet.model.InternshipOffer;
import com.eheiste.laureatnet.model.Post;
import com.eheiste.laureatnet.model.UserAccount;
import com.eheiste.laureatnet.repository.InternshipOfferRepository;
import com.eheiste.laureatnet.service.InternshipOfferService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InternshipOfferServiceImpl implements InternshipOfferService {

    private final InternshipOfferRepository internshipOfferRepository;

    @Override
    public InternshipOffer saveInternshipOffer(InternshipOffer internshipOffer) {
        // Récupérer l'utilisateur connecté depuis le contexte de sécurité
        UserAccount posterIntership = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        // Vérifier si l'utilisateur connecté est le créateur de l'offre de stage
        if (posterIntership.getId() == internshipOffer.getPoster().getId()) {
            // Enregistrer l'offre de stage
            return internshipOfferRepository.save(internshipOffer);
        } else {
            // L'utilisateur connecté n'est pas autorisé à créer cette offre de stage
            throw new IllegalArgumentException("Only the creator can create this internship offer");
        }
    }
    
    @Override
    public InternshipOffer updateInternshipOffer(InternshipOffer existingInternshipOffer, InternshipOffer updatedInternshipOffer) {
    UserAccount posterIntership = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    // Vérifier si l'utilisateur connecté est le créateur de l'offre de stage
    if (posterIntership.getId() == existingInternshipOffer.getPoster().getId()) {
        // Mise à jour de toutes les propriétés de InternshipOffer
        existingInternshipOffer.setContent(updatedInternshipOffer.getContent());
        existingInternshipOffer.setLikesCount(updatedInternshipOffer.getLikesCount());
        existingInternshipOffer.setModifiedAt(LocalDateTime.now().withNano(0));
        existingInternshipOffer.setAttachmentList(updatedInternshipOffer.getAttachmentList());
        existingInternshipOffer.setTechnologyList(updatedInternshipOffer.getTechnologyList());
        existingInternshipOffer.setLikesList(updatedInternshipOffer.getLikesList());
        // Enregistrer l'offre de stage mise à jour
        return internshipOfferRepository.save(existingInternshipOffer);
    } else {
        throw new IllegalArgumentException("Only the creator can update this internship offer");
    }
}

    @Override
    public Optional<InternshipOffer> findInternshipOfferById(long id) {
        return internshipOfferRepository.findById(id);
    }

    @Override
    public Page<InternshipOffer> getAllInternshipOffers(Pageable pageable) {
        return internshipOfferRepository.findAll(pageable);
    }

    @Override
    public Page<InternshipOffer> getAllPostsSorted(Long userId, Pageable pageable) {
        return internshipOfferRepository.findAllSortedByRecommendationGrade(userId, pageable);
    }
    @Override
    public void deleteInternshipOfferById(long id) {
        internshipOfferRepository.deleteById(id);
    }
/* 
    @Override
    public InternshipOffer updateInternshipOffer(InternshipOffer existingInternshipOffer, InternshipOffer updatedInternshipOffer) {
        existingInternshipOffer.setDuration(updatedInternshipOffer.getDuration());
        existingInternshipOffer.setStartDate(updatedInternshipOffer.getStartDate());
        existingInternshipOffer.setEndDate(updatedInternshipOffer.getEndDate());
        existingInternshipOffer.setEntreprise(updatedInternshipOffer.getEntreprise());
        return internshipOfferRepository.save(existingInternshipOffer);
    }*/
}
