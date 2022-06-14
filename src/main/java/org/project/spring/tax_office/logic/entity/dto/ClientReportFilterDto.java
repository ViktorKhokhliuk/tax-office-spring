package org.project.spring.tax_office.logic.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public class ClientReportFilterDto {
    @Getter
    private String status;
    @Getter
    private String type;
    @Getter
    private String date;
    @Getter
    private Long clientId;
    @Getter
    private int page;
}
