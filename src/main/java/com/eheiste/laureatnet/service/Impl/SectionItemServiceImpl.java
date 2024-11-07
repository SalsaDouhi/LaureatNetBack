package com.eheiste.laureatnet.service.Impl;

import com.eheiste.laureatnet.dto.SectionItemDTO;
import com.eheiste.laureatnet.model.SectionItem;
import com.eheiste.laureatnet.model.SectionType;
import com.eheiste.laureatnet.model.UserAccount;
import com.eheiste.laureatnet.repository.SectionItemRepository;
import com.eheiste.laureatnet.repository.SectionTypeRepository;
import com.eheiste.laureatnet.repository.UserAccountRepository;
import com.eheiste.laureatnet.service.SectionItemService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SectionItemServiceImpl implements SectionItemService {

    private SectionItemRepository sectionItemRepository;
    private SectionTypeRepository sectionTypeRepository;
    private UserAccountRepository userAccountRepository;

    @Override
    public SectionItem getSectionItem(Long id) {
        return sectionItemRepository.findById(id).orElseThrow(() -> new RuntimeException("SectionItem not found"));
    }

    @Override
    public List<SectionItem> getAllSectionItems() {
        return sectionItemRepository.findAll();
    }

    @Override
    public SectionItem createSectionItem(SectionItemDTO sectionItemDTO) {
        UserAccount user = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        SectionType sectionType = sectionTypeRepository.findById(sectionItemDTO.getSectionTypeId())
                .orElseThrow(() -> new RuntimeException("SectionType not found for SectionItem"));
        UserAccount userAccount = userAccountRepository.findById(sectionItemDTO.getUserAccountId())
                .orElseThrow(() -> new RuntimeException("UserId not found for SectionItem"));

        // Check if the user creating owns the sectionItem
        if (!sectionItemDTO.getUserAccountId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized to create this section item");
        }

        SectionItem sectionItem = SectionItem.builder()
                .title(sectionItemDTO.getTitle())
                .startDate(sectionItemDTO.getStartDate())
                .endDate(sectionItemDTO.getEndDate())
                .sectionType(sectionType)
                .userAccount(userAccount)
                .build();

        return sectionItemRepository.save(sectionItem);
    }

    @Override
    public SectionItem updateSectionItem(Long id, SectionItemDTO sectionItemDTO) {
        UserAccount user = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SectionItem existing = getSectionItem(id);

        if (!existing.getUserAccount().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized to update this section item");
        }

        existing.setTitle(sectionItemDTO.getTitle());
        existing.setStartDate(sectionItemDTO.getStartDate());
        existing.setEndDate(sectionItemDTO.getEndDate());

        return sectionItemRepository.save(existing);
    }

    @Override
    public void deleteSectionItem(Long id) {
        UserAccount user = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SectionItem sectionItem = getSectionItem(id);

        if (!sectionItem.getUserAccount().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized to delete this section item");
        }

        sectionItemRepository.deleteById(id);
    }
}
