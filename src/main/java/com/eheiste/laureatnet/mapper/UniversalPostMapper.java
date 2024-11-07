package com.eheiste.laureatnet.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eheiste.laureatnet.dto.modelview.UniversalPostDTO;
import com.eheiste.laureatnet.model.Attachment;
import com.eheiste.laureatnet.model.InternshipOffer;
import com.eheiste.laureatnet.model.JobOffer;
import com.eheiste.laureatnet.model.Post;
import com.eheiste.laureatnet.model.ScientificArticlePost;
import com.eheiste.laureatnet.model.Technology;
import com.eheiste.laureatnet.model.UserAccount;
import com.eheiste.laureatnet.dto.modelview.CommentsDTO;

@Component
public class UniversalPostMapper {

	@Autowired
    private CommentsMapper commentsMapper;
	@Autowired
	private LikeShowMapper likeShowMapper;

    public UniversalPostDTO toDTO(Post post) {
        UniversalPostDTO dto = new UniversalPostDTO();

        // Common fields
        dto.setId(post.getId());
        dto.setContent(post.getContent());
        dto.setLikesCount(post.getLikesCount());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setModifiedAt(post.getModifiedAt());

        if(post.getPoster() != null) {
        	dto.setUserId(post.getPoster().getId());
        	dto.setUserUsername(post.getPoster().getUsername());
        }
        
        if(post.getPoster().getUserProfile() != null) {
        	dto.setUserProfileImage(post.getPoster().getUserProfile().getPicture());
        	dto.setUserFullName(post.getPoster().getUserProfile().getFirstName()+" "+post.getPoster().getUserProfile().getLastName());
        }

        // Comments
        if(post.getCommentList() != null) {
	        List<CommentsDTO> commentsDTOs = post.getCommentList().stream()
	            .map(commentsMapper::toDTO)
	            .collect(Collectors.toList());
	        dto.setComments(commentsDTOs);
        }
        if(post.getTechnologyList() != null) {
        	dto.setTechnologies(post.getTechnologyList().stream()
        		.collect(Collectors.toMap(Technology::getId, Technology::getTitle)));
        }

        // Attachments
        if(post.getAttachmentList() != null) {
	        dto.setAttachments(post.getAttachmentList().stream()
	            .map(Attachment::getPath)
	            .collect(Collectors.toList()));
        }
        
        // Likers
        if(post.getLikesList() != null) {
        	dto.setLikes(
        		post.getLikesList()
        		.stream()
        		.map(likeShowMapper::toDTO).toList()
        	);
        }//.collect(Collectors.toList()));

        // Determine the post type and set specific fields
        if (post instanceof InternshipOffer) {
            InternshipOffer internshipOffer = (InternshipOffer) post;
            dto.setPostType("InternshipOffer");
            dto.setInternshipDuration(internshipOffer.getDuration());
            dto.setInternshipStartDate(internshipOffer.getStartDate());
            dto.setInternshipEndDate(internshipOffer.getEndDate());
            dto.setInternshipTitle(internshipOffer.getTitle());
            if (internshipOffer.getEntreprise() != null) {
                dto.setEntrepriseId(internshipOffer.getEntreprise().getId());
                dto.setEntrepriseImage(internshipOffer.getEntreprise().getLogo());
                dto.setEntrepriseName(internshipOffer.getEntreprise().getTitle());
            }
        } else if (post instanceof JobOffer) {
            JobOffer jobOffer = (JobOffer) post;
            dto.setPostType("JobOffer");
            dto.setJobStartDate(jobOffer.getStartDate());
            dto.setJobEndDate(jobOffer.getEndDate());
            dto.setJobTitle(jobOffer.getTitle());
            if (jobOffer.getEntreprise() != null) {
                dto.setEntrepriseId(jobOffer.getEntreprise().getId());
                dto.setEntrepriseImage(jobOffer.getEntreprise().getLogo());
                dto.setEntrepriseName(jobOffer.getEntreprise().getTitle());
            }
        } else if (post instanceof ScientificArticlePost) {
            ScientificArticlePost scientificArticlePost = (ScientificArticlePost) post;
            dto.setPostType("ScientificArticlePost");
            if(scientificArticlePost.getArticle() != null) {
	            dto.setScientificArticleId(scientificArticlePost.getArticle().getId());
	            dto.setScientificArticleTitle(scientificArticlePost.getArticle().getTitle());
	            dto.setScientificArticleDatePublished(scientificArticlePost.getArticle().getDatePublished());
	            dto.setScientificArticlePath(scientificArticlePost.getArticle().getPath());
	            dto.setScientificArticleAuthors(scientificArticlePost.getArticle().getAuthors());
            }
            if(scientificArticlePost.getArticle().getPublisher() != null) {
            	dto.setScientificArticlePublisherId(scientificArticlePost.getArticle().getPublisher().getId());
            	dto.setScientificArticlePublisherName(scientificArticlePost.getArticle().getPublisher().getEmail());
            }
            
        } else {
            dto.setPostType("Post");
        }

        return dto;
    }
}