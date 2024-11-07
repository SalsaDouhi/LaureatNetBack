package com.eheiste.laureatnet.dto.viewmodel;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReportCreationDTO {
    @NotNull(message="mandatory")
    private Long reportId;

    @NotNull(message="mandatory")
    private Long reportTypeId;

    @NotNull(message="mandatory")
    private Long reporterId;

    @NotNull(message="mandatory")
    private Long reportedId;

    @NotNull(message="mandatory")
    private LocalDateTime createdAt;
}
