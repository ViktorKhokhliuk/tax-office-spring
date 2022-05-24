package org.project.spring.tax_office.logic.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.spring.tax_office.logic.entity.user.UserRole;

@Data
@NoArgsConstructor
public class ClientRegistrationDto {
    private String login;
    private String password;
    private String name;
    private String surname;
    private String tin;
    private UserRole userRole = UserRole.CLIENT;
}
