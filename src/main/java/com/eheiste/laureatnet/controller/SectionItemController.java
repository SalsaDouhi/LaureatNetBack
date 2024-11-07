package com.eheiste.laureatnet.controller;

import com.eheiste.laureatnet.dto.SectionItemDTO;
import com.eheiste.laureatnet.model.SectionItem;
import com.eheiste.laureatnet.service.SectionItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/section-items")
@AllArgsConstructor
public class SectionItemController {
    private SectionItemService sectionItemService;

    @GetMapping("/{id}")
    public ResponseEntity<SectionItem> getSectionItem(@PathVariable Long id) {
        try {
            SectionItem sectionItem = sectionItemService.getSectionItem(id);
            return ResponseEntity.ok(sectionItem);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping()
    public List<SectionItem> getAllSectionItems() {
        return sectionItemService.getAllSectionItems();
    }

    @PostMapping
    public ResponseEntity<SectionItem> createSectionItem(@RequestBody SectionItemDTO sectionItemDTO) {
        try {
            SectionItem newItem = sectionItemService.createSectionItem(sectionItemDTO);
            return ResponseEntity.ok(newItem);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SectionItem> updateSectionItem(@PathVariable Long id, @RequestBody SectionItemDTO sectionItemDTO) {
        try {
            System.out.println(sectionItemDTO);
            SectionItem updatedItem = sectionItemService.updateSectionItem(id, sectionItemDTO);
            return ResponseEntity.ok(updatedItem);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSectionItem(@PathVariable Long id) {
        try {
            sectionItemService.deleteSectionItem(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}