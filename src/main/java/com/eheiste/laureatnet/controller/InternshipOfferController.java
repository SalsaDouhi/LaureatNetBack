package com.eheiste.laureatnet.controller;

import com.eheiste.laureatnet.dto.modelview.UniversalPostDTO;
import com.eheiste.laureatnet.dto.viewmodel.InternshipOfferCreationDTO;
import com.eheiste.laureatnet.mapper.InternshipOfferCreationMapper;
import com.eheiste.laureatnet.mapper.UniversalPostMapper;
import com.eheiste.laureatnet.model.InternshipOffer;
import com.eheiste.laureatnet.model.Post;
import com.eheiste.laureatnet.model.UserAccount;
import com.eheiste.laureatnet.service.InternshipOfferService;

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
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/internship-offers")
public class InternshipOfferController {
	
	@Autowired 
	private InternshipOfferService internshipOfferService;
    @Autowired
	private UniversalPostMapper universalPostMapper;
    @Autowired
    private InternshipOfferCreationMapper internshipOfferCreationMapper;
    @GetMapping
    public ResponseEntity<Collection<UniversalPostDTO>> getAllInternshipOffers(
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<InternshipOffer> internshipOffers;

        if (userId == null || userId != ((UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()) {
            internshipOffers = internshipOfferService.getAllInternshipOffers(pageable);
        } else {
            internshipOffers = internshipOfferService.getAllPostsSorted(userId, pageable);
        }

        Collection<UniversalPostDTO> internshipOfferDTOs = internshipOffers
                .stream()
                .map(universalPostMapper::toDTO)
                .collect(Collectors.toList());
        
        return ResponseEntity.status(HttpStatus.OK).body(internshipOfferDTOs);

    }

    @GetMapping("/{id}")
    public ResponseEntity<InternshipOffer> getInternshipOffer(@PathVariable long id) {
        Optional<InternshipOffer> internshipOfferOptional = internshipOfferService.findInternshipOfferById(id);
        return internshipOfferOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInternshipOffer(@PathVariable long id) {
    	InternshipOffer existingPost = internshipOfferService.findInternshipOfferById(id).orElse(null);
        if(existingPost == null || existingPost.getPoster() == null || existingPost.getPoster().getId() != ((UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()) {
        	return ResponseEntity.status(403).build();
        }
        internshipOfferService.deleteInternshipOfferById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<InternshipOffer> createInternshipOffer(@ModelAttribute @Valid InternshipOfferCreationDTO internshipOfferDto, BindingResult bindingResult) {
        if((internshipOfferDto.getPosterId() != ((UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId())) {
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

        InternshipOffer internshipOffer;
		try {
			internshipOffer = internshipOfferCreationMapper.mapToEntity(internshipOfferDto);
		} catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
		}
        return ResponseEntity.status(HttpStatus.CREATED).body(internshipOfferService.saveInternshipOffer(internshipOffer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InternshipOffer> updateInternshipOffer(@PathVariable long id, @RequestBody InternshipOffer updatedInternshipOffer) {
    	InternshipOffer existingPost = internshipOfferService.findInternshipOfferById(id).orElse(null);
        if(existingPost == null) {
        	return ResponseEntity.notFound().build();
        }else if(existingPost.getPoster() == null || existingPost.getPoster().getId() != ((UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()) {
        		return ResponseEntity.status(403).body(null);
        }
        InternshipOffer savedInternshipOffer = internshipOfferService.updateInternshipOffer(existingPost, updatedInternshipOffer);
        return ResponseEntity.ok(savedInternshipOffer);
    }
    
    
}