package org.project.spring.tax_office.logic.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class ReportFilterDto {
    @Getter
    private String status;
    @Getter
    private String type;
    @Getter
    private String date;
    @Getter
    private String name;
    @Getter
    private String surname;
    @Getter
    private String tin;
    @Setter
    @Getter
    private int page;
}
