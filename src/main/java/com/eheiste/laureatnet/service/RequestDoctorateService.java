package com.eheiste.laureatnet.service;

import com.eheiste.laureatnet.model.RequestDoctorate;

import java.util.List;

public interface RequestDoctorateService {
    RequestDoctorate getRequestDoctorate(Long id);

    List<RequestDoctorate> getAllRequestDoctorates();

    RequestDoctorate saveRequestDoctorate(RequestDoctorate requestDoctorate);

    RequestDoctorate updateRequestDoctorate(Long id, boolean accepted);

    void deleteRequestDoctorate(Long id);
}
