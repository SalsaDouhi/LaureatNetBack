package com.eheiste.laureatnet.service.Impl;

import com.eheiste.laureatnet.model.RequestDoctorate;
import com.eheiste.laureatnet.model.UserAccount;
import com.eheiste.laureatnet.repository.RequestDoctorateRepository;
import com.eheiste.laureatnet.service.RequestDoctorateService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RequestDoctorateServiceImpl implements RequestDoctorateService {
    private RequestDoctorateRepository requestDoctorateRepository;

    @Override
    public RequestDoctorate getRequestDoctorate(Long id) {
        return requestDoctorateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RequestDoctorate not found"));
    }

    @Override
    public List<RequestDoctorate> getAllRequestDoctorates() {
        return requestDoctorateRepository.findAll();
    }

    @Override
    public RequestDoctorate saveRequestDoctorate(RequestDoctorate requestDoctorate) {
        UserAccount user = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!requestDoctorate.getCandidate().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized to create this request");
        }
        return requestDoctorateRepository.save(requestDoctorate);
    }

    @Override
    public RequestDoctorate updateRequestDoctorate(Long id, boolean accepted) {
        RequestDoctorate existing = getRequestDoctorate(id);
        UserAccount user = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!existing.getCandidate().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized to update this request");
        }

        existing.setAccepted(accepted);
        return requestDoctorateRepository.save(existing);
    }

    @Override
    public void deleteRequestDoctorate(Long id) {
        RequestDoctorate existing = getRequestDoctorate(id);
        UserAccount user = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!existing.getCandidate().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized to delete this request");
        }

        requestDoctorateRepository.deleteById(id);
    }
}
