package com.eheiste.laureatnet.mapper;

import com.eheiste.laureatnet.dto.ConnectionDTO;
import com.eheiste.laureatnet.dto.ReportDTO;
import com.eheiste.laureatnet.dto.viewmodel.ReportCreationDTO;
import com.eheiste.laureatnet.model.Connection;
import com.eheiste.laureatnet.model.Report;
import com.eheiste.laureatnet.model.ReportType;
import com.eheiste.laureatnet.model.UserAccount;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReportMapper {

    public static ReportDTO mapReportToDTO(Report report) {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setId(report.getId());
        reportDTO.setTitle(report.getReportType().getTitle());
        reportDTO.setReportedFullName(report.getReported().getUserProfile().getFirstName() + " " + report.getReported().getUserProfile().getLastName());
        reportDTO.setReporterFullName(report.getReporter().getUserProfile().getFirstName() + " " + report.getReporter().getUserProfile().getLastName());
        reportDTO.setCreatedAt(report.getCreatedAt());
        return reportDTO;
    }
    public Report mapToEntity(ReportCreationDTO dto) {
        Report report = new Report();
        UserAccount reporter = new UserAccount();
        UserAccount reported = new UserAccount();
        ReportType reportType = new ReportType();

        reporter.setId(dto.getReporterId());
        reported.setId(dto.getReportedId());
        reportType.setId(dto.getReportTypeId());

        report.setReporter(reporter);
        report.setReported(reported);
        report.setReportType(reportType);
        report.setId(dto.getReportId());
        report.setCreatedAt(LocalDateTime.now());

        return report;
    }
}
