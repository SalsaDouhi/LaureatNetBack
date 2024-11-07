package com.eheiste.laureatnet.dto.viewmodel;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class BlockCreationDTO {
    @NotNull(message="mandatory")
    private Long blockId;

    @NotNull(message="mandatory")
    private Long blockerId;

    @NotNull(message="mandatory")
    private Long blockedId;

    @NotNull(message="mandatory")
    private LocalDateTime createdAt;
}


