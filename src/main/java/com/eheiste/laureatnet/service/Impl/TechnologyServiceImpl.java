package com.eheiste.laureatnet.service.Impl;


import com.eheiste.laureatnet.model.Technology;
import com.eheiste.laureatnet.repository.TechnologyRepository;
import com.eheiste.laureatnet.service.TechnologyService;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
@Service
public class TechnologyServiceImpl implements TechnologyService {

    @Autowired
    private TechnologyRepository technologyRepository;

    @Override
    public Collection<Technology> getAllTechnologies() {
        return technologyRepository.findAll();
    }

    @Override
    public Optional<Technology> findTechnologyById(Long id) {
        return technologyRepository.findById(id);
    }

    @Override
    public void deleteTechnologyById(Long id) {
        technologyRepository.deleteById(id);
    }

    @Override
    public Technology saveTechnology(Technology technology) {
        return technologyRepository.save(technology);
    }


}
