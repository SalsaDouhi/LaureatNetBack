package com.eheiste.laureatnet.service.Impl;

import com.eheiste.laureatnet.model.Report;
import com.eheiste.laureatnet.model.ReportType;
import com.eheiste.laureatnet.model.UserAccount;
import com.eheiste.laureatnet.repository.ReportRepository;
import com.eheiste.laureatnet.repository.ReportTypeRepository;
import com.eheiste.laureatnet.repository.UserAccountRepository;
import com.eheiste.laureatnet.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {
    private ReportRepository reportRepository;
    private ReportTypeRepository reportTypeRepository;
    private UserAccountRepository userAccountRepository;

    public Report getReport(Long id) {
        return reportRepository.findById(id).orElseThrow(() -> new RuntimeException("Report not found"));
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    @Transactional
    public Report createReport(Report report) {
        ReportType reportType = reportTypeRepository.findById(report.getReportType().getId())
                .orElseThrow(() -> new RuntimeException("Report Type not found"));

        UserAccount reporter = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserAccount reported = userAccountRepository.findById(report.getReported().getId())
                .orElseThrow(() -> new RuntimeException("Reported user not found"));

        report.setReportType(reportType);
        report.setReporter(reporter);
        report.setReported(reported);

        return reportRepository.save(report);
    }

    public Report updateReport(Long id, Report updatedReport) {
        Report report = reportRepository.findById(id).orElseThrow(() -> new RuntimeException("Report not found"));
        report.setStatus(updatedReport.isStatus());
        report.setContent(updatedReport.getContent());
        report.setReportType(updatedReport.getReportType());
        report.setReporter(updatedReport.getReporter());
        report.setReported(updatedReport.getReported());
        return reportRepository.save(report);
    }
}
