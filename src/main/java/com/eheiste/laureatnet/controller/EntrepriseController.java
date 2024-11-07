package com.eheiste.laureatnet.controller;

import com.eheiste.laureatnet.dto.viewmodel.EntrepriseDTO;
import com.eheiste.laureatnet.mapper.EntrepriseMapper;
import com.eheiste.laureatnet.model.Entreprise;
import com.eheiste.laureatnet.service.EntrepriseService;
import com.eheiste.laureatnet.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/entreprises")
public class EntrepriseController {

    @Autowired
    private EntrepriseService entrepriseService;
    @Autowired
    private EntrepriseMapper entrepriseMapper;
    @Autowired
    private FileStorageService fileStorageService;
    @GetMapping
    public Collection<Entreprise> getAllEntreprises(){
        return entrepriseService.getAllEntreprises();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entreprise> getEntreprise(@PathVariable long id){
        Entreprise entreprise = entrepriseService.findEntrepriseById(id);
        if (entreprise != null) {
            return ResponseEntity.ok(entreprise);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntrepriseById(@PathVariable long id){
        entrepriseService.deleteEntrepriseById(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Entreprise> createEntreprise(@RequestPart("entreprise")  EntrepriseDTO entrepriseDTO ,@RequestPart("logo") MultipartFile file) throws IOException {

        System.out.println(entrepriseDTO);
        //MultipartFile file = entrepriseDTO.getLogo();
        if (!file.isEmpty()) {
            Map.Entry<String,String> PathAndType = fileStorageService.storeFile(file).entrySet().iterator().next();
            String logoPath = PathAndType.getKey();
            Entreprise entreprise = entrepriseMapper.mapToEntity(entrepriseDTO);
            entreprise.setLogo(logoPath);
            return ResponseEntity.status(HttpStatus.CREATED).body(entrepriseService.saveEntreprise(entreprise));
        } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }/*
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Entreprise> createEntreprise(@RequestPart("entreprise") Entreprise entreprise,@RequestPart("logo") MultipartFile file) throws IOException {
        Path path  = Paths.get(System.getProperty("user.home"),"laureaNet","entrepriseImg");//Entreprise entreprise = new Entreprise();
        if(!Files.exists(path)){
            Files.createDirectories(path);
        } if (!file.isEmpty()) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String uniqueFileName = UUID.randomUUID().toString() + "-" + fileName;
            Path filePath = Paths.get(System.getProperty("user.home"), "laureaNet", "entrepriseImg", uniqueFileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            entreprise.setLogo(filePath.toString());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(entrepriseService.saveEntreprise(entreprise));
    }
    /*@PostMapping

    public ResponseEntity<Entreprise> createEntreprise(@RequestBody Entreprise entreprise){
        return ResponseEntity.status(HttpStatus.CREATED).body(entrepriseService.saveEntreprise(entreprise));
    }*/
    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Entreprise> updateEntreprise(@PathVariable long id,
                                                       @RequestPart("entreprise") EntrepriseDTO entrepriseDTO,
                                                       @RequestParam(value = "logo", required = false) MultipartFile file) throws IOException {


        System.out.println(entrepriseDTO);

        Entreprise existingEntreprise = entrepriseService.findEntrepriseById(id);
        if (existingEntreprise != null) {
            if (file != null && !file.isEmpty()) {
                Map.Entry<String,String> PathAndType = fileStorageService.storeFile(file).entrySet().iterator().next();
                String logoPath = PathAndType.getKey();
                existingEntreprise.setLogo(logoPath);
            }
            existingEntreprise.setTitle(entrepriseDTO.getTitle());
            existingEntreprise.setLocalisationX(entrepriseDTO.getLocalisationX());
            existingEntreprise.setLocalisationY(entrepriseDTO.getLocalisationY());
            existingEntreprise.setTel(entrepriseDTO.getTel());
            existingEntreprise.setEmail(entrepriseDTO.getEmail());
            existingEntreprise.setWebsite(entrepriseDTO.getWebsite());

            entrepriseService.saveEntreprise(existingEntreprise);
            return ResponseEntity.ok(existingEntreprise);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}