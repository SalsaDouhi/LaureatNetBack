package com.eheiste.laureatnet.controller;

import com.eheiste.laureatnet.dto.ConnectionDTO;
import com.eheiste.laureatnet.dto.ReportDTO;
import com.eheiste.laureatnet.dto.viewmodel.ConnectionCreationDTO;
import com.eheiste.laureatnet.dto.viewmodel.ReportCreationDTO;
import com.eheiste.laureatnet.mapper.ConnectionMapper;
import com.eheiste.laureatnet.mapper.ReportMapper;
import com.eheiste.laureatnet.model.Connection;
import com.eheiste.laureatnet.model.Report;
import com.eheiste.laureatnet.service.ReportService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@AllArgsConstructor
@NoArgsConstructor
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportMapper reportMapper;

    @GetMapping("/{id}")
    public Report getReport(@PathVariable Long id) {
        return reportService.getReport(id);
    }

    @GetMapping()
    public ResponseEntity<Collection<ReportDTO>> getReports() {
        Collection<Report> reports = reportService.getAllReports();
        if (reports != null && !reports.isEmpty()) {
            Collection<ReportDTO> reportDTOS = new ArrayList<>();
            for (Report report : reports) {
                ReportDTO reportDTO = ReportMapper.mapReportToDTO(report);
                reportDTOS.add(reportDTO);
            }
            return ResponseEntity.ok(reportDTOS);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Report> createReport(@RequestBody ReportCreationDTO reportCreationDTO){
        Report report = reportMapper.mapToEntity(reportCreationDTO);
        return  ResponseEntity.status(HttpStatus.CREATED).body(reportService.createReport(report));
    }

    @PutMapping("/{id}")
    public Report updateReport(@PathVariable Long id, @RequestBody Report report) {
        return reportService.updateReport(id, report);
    }
}