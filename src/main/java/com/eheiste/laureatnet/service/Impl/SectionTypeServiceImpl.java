package com.eheiste.laureatnet.service.Impl;

import com.eheiste.laureatnet.model.SectionType;
import com.eheiste.laureatnet.repository.SectionTypeRepository;
import com.eheiste.laureatnet.service.SectionTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SectionTypeServiceImpl implements SectionTypeService {
    private SectionTypeRepository sectionTypeRepository;

    @Override
    public SectionType getSectionType(Long id) {
        return sectionTypeRepository.findById(id).orElseThrow(() -> new RuntimeException("SectionType not found"));
    }

    @Override
    public List<SectionType> getAllSectionTypes() {
        return sectionTypeRepository.findAll();
    }

    @Override
    public SectionType saveSectionType(SectionType sectionType) {
        return sectionTypeRepository.save(sectionType);
    }

    @Override
    public SectionType updateSectionType(Long id, SectionType sectionType) {
        SectionType existing = getSectionType(id);
        existing.setTitle(sectionType.getTitle());
        return sectionTypeRepository.save(existing);
    }

    @Override
    public void deleteSectionType(Long id) {
        sectionTypeRepository.deleteById(id);
    }
}
