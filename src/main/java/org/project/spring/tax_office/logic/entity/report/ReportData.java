package org.project.spring.tax_office.logic.entity.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportData {

    private Long id;
    private String person;
    private String nationality;
    private String taxYear;
    private Integer quarter;
    private Integer monthNumber;
    private String taxGroup;
    private String activity;
    private String income;

}
