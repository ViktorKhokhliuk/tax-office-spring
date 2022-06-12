package org.project.spring.tax_office.logic.controller.user;

import org.project.spring.tax_office.logic.entity.user.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class UserControllerConfig {

    @Bean
    public Map<UserRole, String> userViews() {
        return Map.of(UserRole.INSPECTOR, "/inspector/homePage.jsp", UserRole.CLIENT, "/client/homePage.jsp");
    }
}
