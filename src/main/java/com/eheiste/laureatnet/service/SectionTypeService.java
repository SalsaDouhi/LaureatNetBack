package com.eheiste.laureatnet.service;

import com.eheiste.laureatnet.model.SectionType;

import java.util.List;

public interface SectionTypeService {
    SectionType getSectionType(Long id);
    List<SectionType> getAllSectionTypes();
    SectionType saveSectionType(SectionType sectionType);
    SectionType updateSectionType(Long id, SectionType sectionType);
    void deleteSectionType(Long id);
}
