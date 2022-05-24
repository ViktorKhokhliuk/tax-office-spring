package org.project.spring.tax_office.logic.entity.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report {

    private Long id;
    private String title;
    private String date;
    private String type;
    private String status;
    private String info;
    private Long clientId;

}
