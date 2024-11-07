package com.eheiste.laureatnet.service;

import com.eheiste.laureatnet.dto.SectionItemDTO;
import com.eheiste.laureatnet.model.SectionItem;

import java.util.List;

public interface SectionItemService {
    SectionItem getSectionItem(Long id);
    List<SectionItem> getAllSectionItems();
    SectionItem createSectionItem(SectionItemDTO sectionItemDTO);
    SectionItem updateSectionItem(Long id, SectionItemDTO sectionItemDTO);
    void deleteSectionItem(Long id);
}
