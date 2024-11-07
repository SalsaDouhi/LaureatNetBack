package com.eheiste.laureatnet.mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eheiste.laureatnet.dto.viewmodel.JobOfferCreationDTO;
import com.eheiste.laureatnet.model.Attachment;
import com.eheiste.laureatnet.model.Entreprise;
import com.eheiste.laureatnet.model.JobOffer;
import com.eheiste.laureatnet.model.Technology;
import com.eheiste.laureatnet.model.UserAccount;
import com.eheiste.laureatnet.service.FileStorageService;
import com.eheiste.laureatnet.service.TechnologyService;


@Component
public class JobOfferCreationMapper {

    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private TechnologyService technologyService;

    public JobOffer mapToEntity(JobOfferCreationDTO dto) throws Exception {
        JobOffer jobOffer = new JobOffer();
        UserAccount poster = new UserAccount();
        poster.setId(dto.getPosterId());
        jobOffer.setPoster(poster);
        jobOffer.setContent(dto.getContent());

        // Assuming you have an Entreprise entity
        Entreprise entreprise = new Entreprise();
        entreprise.setId(dto.getEntrepriseId());
        jobOffer.setEntreprise(entreprise);

        jobOffer.setStartDate(dto.getStartDate());
        jobOffer.setEndDate(dto.getEndDate());
        jobOffer.setTitle(dto.getJobTitle());

        // Handle file uploads
        if (dto.getAttachments() != null && !dto.getAttachments().isEmpty()) {
            Map<String, String> attachmentPaths = fileStorageService.storeFiles(dto.getAttachments());
            List<Attachment> attachmentList = new ArrayList<Attachment>();
            int order = 1;
            for (Map.Entry<String, String> entry : attachmentPaths.entrySet()) {
                Attachment att = new Attachment();
                att.setPath(entry.getKey()); // Set path from the map key
                att.setType(entry.getValue()); // Set type from the map value
                att.setOrder(order++); // Set order and increment counter
                att.setPost(jobOffer);
                attachmentList.add(att);
            }
            jobOffer.setAttachmentList(attachmentList);
        }
        if(dto.getTechnologyList()!=null && !(dto.getTechnologyList().isEmpty())) {
        	ArrayList<Technology> techList = new ArrayList<Technology>();
        	for(var techId : dto.getTechnologyList()) {
        		Technology tech = technologyService.findTechnologyById(techId).orElse(null);
        		if(tech==null) {
        			throw new Exception("Invalid thechnology Id");
        		}
        		techList.add(tech);
        	}
        	jobOffer.setTechnologyList(techList);
        }

        jobOffer.setCreatedAt(LocalDateTime.now());
        jobOffer.setModifiedAt(LocalDateTime.now());
        return jobOffer;
    }
}


