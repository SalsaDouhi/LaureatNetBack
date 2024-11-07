package com.eheiste.laureatnet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockDTO {
    private Long id;
    private String fullname;
    private LocalDateTime createdAt;
}
