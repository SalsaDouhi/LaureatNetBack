package com.eheiste.laureatnet.mapper;


import com.eheiste.laureatnet.dto.ReportTypeDTO;
import com.eheiste.laureatnet.dto.viewmodel.ConnectionCreationDTO;
import com.eheiste.laureatnet.model.Connection;
import com.eheiste.laureatnet.model.ReportType;
import com.eheiste.laureatnet.model.UserAccount;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReportTypeMapper {
    public static ReportTypeDTO mapToEntity(ReportType reportType) {
        ReportTypeDTO reportTypeDTO = new ReportTypeDTO();

        reportTypeDTO.setId(reportType.getId());
        reportTypeDTO.setTitle(reportType.getTitle());

        return reportTypeDTO;
    }
}
