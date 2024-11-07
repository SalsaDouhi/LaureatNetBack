package com.eheiste.laureatnet.controller;

import com.eheiste.laureatnet.dto.modelview.UniversalPostDTO;
import com.eheiste.laureatnet.dto.viewmodel.JobOfferCreationDTO;
import com.eheiste.laureatnet.mapper.JobOfferCreationMapper;
import com.eheiste.laureatnet.mapper.UniversalPostMapper;
import com.eheiste.laureatnet.model.InternshipOffer;
import com.eheiste.laureatnet.model.JobOffer;
import com.eheiste.laureatnet.model.UserAccount;
import com.eheiste.laureatnet.service.JobOfferService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/job-offers") 
public class JobOfferController {

    @Autowired
    private JobOfferService jobOfferService;
    @Autowired
	private JobOfferCreationMapper jobOfferCreationMapper;
    @Autowired
	private UniversalPostMapper universalPostMapper;

    @GetMapping
    public ResponseEntity<Collection<UniversalPostDTO>> getAllJobOffers(
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<JobOffer> jobOffers;

        if (userId == null || userId != ((UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()) {
            jobOffers = jobOfferService.getAllJobOffers(pageable);
        } else {
            jobOffers = jobOfferService.getAllPostsSorted(userId, pageable);
        }

        Collection<UniversalPostDTO> jobOfferDTOs = jobOffers
                .stream()
                .map(universalPostMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(jobOfferDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobOffer> getJobOfferById(@PathVariable long id) {
        JobOffer jobOffer = jobOfferService.findJobOfferById(id).orElse(null);
        if (jobOffer != null) {
            return ResponseEntity.ok(jobOffer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<JobOffer> createJobOffer(@ModelAttribute @Valid JobOfferCreationDTO jobOfferDto, BindingResult bindingResult) {
        if((jobOfferDto.getPosterId() != ((UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId())) {
            System.out.println("not your account");
            return ResponseEntity.status(403).body(null);
        }
        if((SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().contentEquals("ROLE_LAUREAT"))==false)
           ) {
        System.out.println("dont have the permissions");
        SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .forEach(authority -> {System.out.println( authority.getAuthority());
                System.out.println("the equality check is : "+authority.getAuthority().contentEquals("ROLE_DOC"));});
            return ResponseEntity.status(403).body(null);
        }

        if (bindingResult.hasFieldErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                System.out.println("Field " + error.getField() + ": " + error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(null);
        }

        JobOffer jobOffer;
		try {
			jobOffer = jobOfferCreationMapper.mapToEntity(jobOfferDto);
		} catch (Exception e){
            return ResponseEntity.badRequest().body(null);
		}
        return ResponseEntity.status(HttpStatus.CREATED).body(jobOfferService.saveJobOffer(jobOffer));
    }

    /*@PutMapping("/{id}")
    public ResponseEntity<JobOffer> updateJobOffer(@PathVariable long id, @RequestBody JobOffer jobOffer) {
        JobOffer existingJobOffer = jobOfferService.findJobOfferById(id);
        if (existingJobOffer != null) {
            jobOffer.setId(id); // Assurez-vous que l'ID correspond
            return ResponseEntity.ok(jobOfferService.saveJobOffer(jobOffer));
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/
    @PutMapping("/{id}")
    public ResponseEntity<JobOffer> updateJobOffer(@PathVariable long id, @RequestBody JobOffer updatedJobOffer) {
        JobOffer existingPost = jobOfferService.findJobOfferById(id).orElse(null);
        if(existingPost == null) {
        	return ResponseEntity.notFound().build();
        }else if(existingPost.getPoster() == null || existingPost.getPoster().getId() != ((UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()) {
        	return ResponseEntity.status(403).body(null);
        }
        JobOffer savedJobOffer = jobOfferService.updateJobOffer(existingPost, updatedJobOffer);
        return ResponseEntity.ok(savedJobOffer);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobOffer(@PathVariable long id) {
        JobOffer existingPost = jobOfferService.findJobOfferById(id).orElse(null);
        if(existingPost == null || existingPost.getPoster() == null || existingPost.getPoster().getId() != ((UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()) {
        	return ResponseEntity.status(403).build();
        }
        jobOfferService.deleteJobOfferById(id);
        return ResponseEntity.noContent().build();
    }
}