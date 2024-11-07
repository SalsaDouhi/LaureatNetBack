package com.eheiste.laureatnet.service;

import com.eheiste.laureatnet.model.Technology;

import java.util.Collection;
import java.util.Optional;

public interface TechnologyService {

    Collection<Technology> getAllTechnologies();

    Optional<Technology> findTechnologyById(Long id);

    void deleteTechnologyById(Long id);

    Technology saveTechnology(Technology technology);
}