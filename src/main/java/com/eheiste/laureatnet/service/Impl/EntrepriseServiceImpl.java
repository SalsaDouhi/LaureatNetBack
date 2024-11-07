package com.eheiste.laureatnet.service.Impl;

import com.eheiste.laureatnet.model.Entreprise;
import com.eheiste.laureatnet.repository.EntrepriseRepository;
import com.eheiste.laureatnet.service.EntrepriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class EntrepriseServiceImpl implements EntrepriseService {

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @Override
    public Collection<Entreprise> getAllEntreprises() {
        return entrepriseRepository.findAll();
    }

    @Override
    public Entreprise findEntrepriseById(Long id) {
        Optional<Entreprise> entrepriseOptional = entrepriseRepository.findById(id);
        return entrepriseOptional.orElse(null);
    }

    @Override
    public void deleteEntrepriseById(Long id) {
        entrepriseRepository.deleteById(id);
    }

    @Override
    public Entreprise saveEntreprise(Entreprise entreprise) {
        return entrepriseRepository.save(entreprise);
    }
}
