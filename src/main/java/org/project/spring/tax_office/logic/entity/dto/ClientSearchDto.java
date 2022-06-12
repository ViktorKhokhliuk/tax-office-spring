package org.project.spring.tax_office.logic.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ClientSearchDto {
    @Getter
    private String name;
    @Getter
    private String surname;
    @Getter
    private String tin;
    @Getter
    private int page;
}
