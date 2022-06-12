package org.project.spring.tax_office.logic.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ReportCreateDto {
    @Getter
    private Long clientId;
    @Getter
    private String title;
    @Getter
    private String type;
}
