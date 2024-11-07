package com.eheiste.laureatnet.controller;

import com.eheiste.laureatnet.dto.ConnectionDTO;
import com.eheiste.laureatnet.dto.ReportTypeDTO;
import com.eheiste.laureatnet.mapper.ConnectionMapper;
import com.eheiste.laureatnet.mapper.ReportTypeMapper;
import com.eheiste.laureatnet.model.Connection;
import com.eheiste.laureatnet.model.ReportType;
import com.eheiste.laureatnet.service.ReportTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/report-types")
public class ReportTypeController {

    @Autowired
    private ReportTypeService reportTypeService;

    @GetMapping()
    public ResponseEntity<Collection<ReportTypeDTO>> getAllReportTypes(){
        Collection<ReportType> reportTypes = reportTypeService.getAllReportTypes();
        if (reportTypes != null && !reportTypes.isEmpty()) {
            Collection<ReportTypeDTO> reportTypeDTOS = new ArrayList<>();
            for (ReportType reportType : reportTypes) {
                ReportTypeDTO reportTypeDTO = ReportTypeMapper.mapToEntity(reportType);
                reportTypeDTOS.add(reportTypeDTO);
            }
            return ResponseEntity.ok(reportTypeDTOS);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
