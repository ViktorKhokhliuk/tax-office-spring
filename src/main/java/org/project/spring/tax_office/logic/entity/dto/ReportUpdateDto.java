package org.project.spring.tax_office.logic.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ReportUpdateDto {
    @Getter
    private String updatedStatus;
    @Getter
    private String status;
    @Getter
    private String info;
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
    @Getter
    private String clientFullName;
    @Getter
    private Long reportId;
    @Getter
    private Long clientId;
    @Getter
    private int page;
}
