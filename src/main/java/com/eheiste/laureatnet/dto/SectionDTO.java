package com.eheiste.laureatnet.dto;

import com.eheiste.laureatnet.model.SectionItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionDTO {
    private Long id;
    private String title;
    private List<SectionItem> items;
}
