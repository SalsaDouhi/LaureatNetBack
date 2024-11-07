package com.eheiste.laureatnet.mapper;

import com.eheiste.laureatnet.dto.viewmodel.EntrepriseDTO;
import com.eheiste.laureatnet.model.Entreprise;
import org.springframework.stereotype.Component;

@Component
public class EntrepriseMapper {

    /*public EntrepriseDTO mapToDTO(Entreprise entreprise) {
        EntrepriseDTO dto = new EntrepriseDTO();
        dto.setId(entreprise.getId());
        dto.setTitle(entreprise.getTitle());
        dto.setLocalisationX(entreprise.getLocalisationX());
        dto.setLocalisationY(entreprise.getLocalisationY());
        dto.setTel(entreprise.getTel());
        dto.setEmail(entreprise.getEmail());
        dto.setWebsite(entreprise.getWebsite());
        // Mapping du logo
        // Vous pouvez gérer ici la conversion du logo MultipartFile en String
        // dto.setLogo(entreprise.getLogo());
        return dto;
    }*/

    public Entreprise mapToEntity(EntrepriseDTO dto) {
        Entreprise entreprise = new Entreprise();
        //entreprise.setId(dto.getId());
        entreprise.setTitle(dto.getTitle());
        entreprise.setLocalisationX(dto.getLocalisationX());
        entreprise.setLocalisationY(dto.getLocalisationY());
        entreprise.setTel(dto.getTel());
        entreprise.setEmail(dto.getEmail());
        entreprise.setWebsite(dto.getWebsite());
       // String Logo = "pas de logo";
        //traitement de upload fichier
        //entreprise.setLogo(Logo);
        // Mapping du logo
        // Vous pouvez gérer ici la conversion de String en MultipartFile
        // entreprise.setLogo(dto.getLogo());
        return entreprise;
    }
}
