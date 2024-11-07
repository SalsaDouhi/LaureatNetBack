package com.eheiste.laureatnet.service;

import com.eheiste.laureatnet.model.Entreprise;

import java.util.Collection;

public interface EntrepriseService {

    Collection<Entreprise> getAllEntreprises();

    Entreprise findEntrepriseById(Long id);

    void deleteEntrepriseById(Long id);

    Entreprise saveEntreprise(Entreprise entreprise);
}
