package com.eheiste.laureatnet.mapper;

import com.eheiste.laureatnet.dto.viewmodel.ExistingArticlePostCreationDTO;
import com.eheiste.laureatnet.dto.viewmodel.PostCreationDTO;
import com.eheiste.laureatnet.model.Attachment;
import com.eheiste.laureatnet.model.Post;
import com.eheiste.laureatnet.model.Technology;
import com.eheiste.laureatnet.model.UserAccount;
import com.eheiste.laureatnet.service.FileStorageService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostCreationMapper {
	@Autowired
	private FileStorageService fileStorageService;

    public Post mapToEntity(PostCreationDTO dto) {
    	Post post = new Post();
    	UserAccount poster = new UserAccount();
    	poster.setId(dto.getPosterId());
    	post.setPoster(poster);
        post.setContent(dto.getContent());
        
        //traitement de upload fichier
        //
        if(dto.getAttachmentList()!=null && !dto.getAttachmentList().isEmpty()) {
	        Map<String,String> attachmentPaths = fileStorageService.storeFiles(dto.getAttachmentList());
	        List<Attachment> attachmentList = new ArrayList<Attachment>();
	        int ordre = 1; 
	        for (Map.Entry<String, String> entry : attachmentPaths.entrySet()) {
	            Attachment att = new Attachment();
	            att.setPath(entry.getKey()); // Set path from the map key
	            att.setType(entry.getValue()); // Set type from the map value
	            att.setOrder(ordre++); // Set ordre and increment counter
	            att.setPost(post);
	            attachmentList.add(att);
	        }
	        post.setAttachmentList(attachmentList);
        }
        

    	post.setCreatedAt(LocalDateTime.now());
    	post.setModifiedAt(LocalDateTime.now());
        return post;
    }
}
