package com.eheiste.laureatnet.mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eheiste.laureatnet.dto.viewmodel.InternshipOfferCreationDTO;
import com.eheiste.laureatnet.dto.viewmodel.JobOfferCreationDTO;
import com.eheiste.laureatnet.model.Attachment;
import com.eheiste.laureatnet.model.Entreprise;
import com.eheiste.laureatnet.model.InternshipOffer;
import com.eheiste.laureatnet.model.JobOffer;
import com.eheiste.laureatnet.model.Technology;
import com.eheiste.laureatnet.model.UserAccount;
import com.eheiste.laureatnet.service.FileStorageService;
import com.eheiste.laureatnet.service.TechnologyService;


@Component
public class InternshipOfferCreationMapper {

    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private TechnologyService technologyService;

    public InternshipOffer mapToEntity(InternshipOfferCreationDTO dto) throws Exception {
        InternshipOffer internshipOffer = new InternshipOffer();
        UserAccount poster = new UserAccount();
        poster.setId(dto.getPosterId());
        internshipOffer.setPoster(poster);
        internshipOffer.setContent(dto.getContent());

        // Assuming you have an Entreprise entity
        Entreprise entreprise = new Entreprise();
        entreprise.setId(dto.getEntrepriseId());
        internshipOffer.setEntreprise(entreprise);

        internshipOffer.setTitle(dto.getRoleTitle());
        internshipOffer.setStartDate(dto.getStartDate());
        internshipOffer.setEndDate(dto.getEndDate());
        internshipOffer.setDuration(dto.getDuration());

        // Handle file uploads
        if (dto.getAttachments() != null && !dto.getAttachments().isEmpty()) {
            Map<String, String> attachmentPaths = fileStorageService.storeFiles(dto.getAttachments());
            List<Attachment> attachmentList = new ArrayList<>();
            int order = 1;
            for (Map.Entry<String, String> entry : attachmentPaths.entrySet()) {
                Attachment att = new Attachment();
                att.setPath(entry.getKey());
                att.setType(entry.getValue());
                att.setOrder(order++);
                att.setPost(internshipOffer);
                attachmentList.add(att);
            }
            internshipOffer.setAttachmentList(attachmentList);
        }

        // Map technologies
        if(dto.getTechnologyList()!=null && !(dto.getTechnologyList().isEmpty())) {
        	ArrayList<Technology> techList = new ArrayList<Technology>();
        	for(var techId : dto.getTechnologyList()) {
        		Technology tech = technologyService.findTechnologyById(techId).orElse(null);
        		if(tech==null) {
        			throw new Exception("Invalid thechnology Id");
        		}
        		techList.add(tech);
        	}
        	internshipOffer.setTechnologyList(techList);
        }

        internshipOffer.setCreatedAt(LocalDateTime.now());
        internshipOffer.setModifiedAt(LocalDateTime.now());
        return internshipOffer;
    }
}