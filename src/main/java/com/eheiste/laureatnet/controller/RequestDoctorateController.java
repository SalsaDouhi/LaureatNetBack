package com.eheiste.laureatnet.controller;

import com.eheiste.laureatnet.model.RequestDoctorate;
import com.eheiste.laureatnet.service.RequestDoctorateService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/request-doctorates")
@AllArgsConstructor
@NoArgsConstructor
public class RequestDoctorateController {
    private RequestDoctorateService requestDoctorateService;

    @GetMapping("/{id}")
    public ResponseEntity<RequestDoctorate> getRequestDoctorate(@PathVariable Long id) {
        try {
            RequestDoctorate requestDoctorate = requestDoctorateService.getRequestDoctorate(id);
            return ResponseEntity.ok(requestDoctorate);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping()
    public List<RequestDoctorate> getAllRequestDoctorates() {
        return requestDoctorateService.getAllRequestDoctorates();
    }

    @PostMapping
    public ResponseEntity<RequestDoctorate> createRequestDoctorate(@RequestBody RequestDoctorate requestDoctorate) {
        try {
            RequestDoctorate newRequest = requestDoctorateService.saveRequestDoctorate(requestDoctorate);
            return ResponseEntity.ok(newRequest);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RequestDoctorate> updateRequestDoctorate(@PathVariable Long id, @RequestParam boolean accepted) {
        try {
            RequestDoctorate updatedRequest = requestDoctorateService.updateRequestDoctorate(id, accepted);
            return ResponseEntity.ok(updatedRequest);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequestDoctorate(@PathVariable Long id) {
        try {
            requestDoctorateService.deleteRequestDoctorate(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
