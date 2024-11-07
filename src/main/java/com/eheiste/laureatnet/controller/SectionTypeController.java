package com.eheiste.laureatnet.controller;

import com.eheiste.laureatnet.model.SectionType;
import com.eheiste.laureatnet.service.SectionTypeService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/section-types")
@AllArgsConstructor
@NoArgsConstructor
public class SectionTypeController {
    private SectionTypeService sectionTypeService;

    @GetMapping("/{id}")
    public ResponseEntity<SectionType> getSectionType(@PathVariable Long id) {
        try {
            SectionType sectionType = sectionTypeService.getSectionType(id);
            return ResponseEntity.ok(sectionType);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping()
    public List<SectionType> getAllSectionTypes() {
        return sectionTypeService.getAllSectionTypes();
    }

    @PostMapping
    public ResponseEntity<SectionType> createSectionType(@RequestBody SectionType sectionType) {
        try {
            SectionType newSectionType = sectionTypeService.saveSectionType(sectionType);
            return ResponseEntity.ok(newSectionType);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SectionType> updateSectionType(@PathVariable Long id, @RequestBody SectionType sectionType) {
        try {
            SectionType updatedSectionType = sectionTypeService.updateSectionType(id, sectionType);
            return ResponseEntity.ok(updatedSectionType);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSectionType(@PathVariable Long id) {
        try {
            sectionTypeService.deleteSectionType(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}