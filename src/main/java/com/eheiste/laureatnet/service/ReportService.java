package com.eheiste.laureatnet.service;

import com.eheiste.laureatnet.model.Report;

import java.util.List;

public interface ReportService {
    Report getReport(Long id);

    List<Report> getAllReports();

    Report createReport(Report report);

    Report updateReport(Long id, Report updatedReport);
}
