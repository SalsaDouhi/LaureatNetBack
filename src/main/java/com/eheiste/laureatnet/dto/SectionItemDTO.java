package com.eheiste.laureatnet.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize
public class SectionItemDTO {
    private Long id;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long userAccountId;
    private Long sectionTypeId;
}
