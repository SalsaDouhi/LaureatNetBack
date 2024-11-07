package com.eheiste.laureatnet.controller;

import com.eheiste.laureatnet.service.TechnologyService;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eheiste.laureatnet.model.Technology;

@RestController
@RequestMapping("/api/v1/technologies")
public class TechnologyController {

    @Autowired
    private TechnologyService technologyService;

    @GetMapping
    public Collection<Technology> getAllTechnologies() {
        return technologyService.getAllTechnologies();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Technology> getTechnology(@PathVariable long id) {
        Technology technology = technologyService.findTechnologyById(id).orElse(null);
        if (technology != null) {
            return ResponseEntity.ok(technology);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteTechnology(@PathVariable long id) {
        technologyService.deleteTechnologyById(id);
    }

    @PostMapping
    public ResponseEntity<Technology> createTechnology(@RequestBody Technology technology) {
        return ResponseEntity.status(HttpStatus.CREATED).body(technologyService.saveTechnology(technology));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Technology> updateTechnology(@PathVariable long id, @RequestBody Technology technology) {
        Technology existingTechnology = technologyService.findTechnologyById(id).orElse(null);
        if (existingTechnology != null) {
            existingTechnology.setTitle(technology.getTitle());
            //existingTechnology.setTechnologyPost(technology.getTechnologyPost());
            technologyService.saveTechnology(existingTechnology);
            return ResponseEntity.ok(existingTechnology);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}