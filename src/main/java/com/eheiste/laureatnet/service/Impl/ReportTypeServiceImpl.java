package com.eheiste.laureatnet.service.Impl;

import com.eheiste.laureatnet.model.ReportType;
import com.eheiste.laureatnet.model.SectionType;
import com.eheiste.laureatnet.repository.ReportTypeRepository;
import com.eheiste.laureatnet.service.ReportTypeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportTypeServiceImpl implements ReportTypeService {

    @Autowired
    private ReportTypeRepository reportTypeRepository;

    @Override
    public List<ReportType> getAllReportTypes() {
        System.out.printf("test1");
        return reportTypeRepository.findAll();
    }
}
