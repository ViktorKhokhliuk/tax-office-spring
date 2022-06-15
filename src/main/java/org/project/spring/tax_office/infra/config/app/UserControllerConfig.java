package org.project.spring.tax_office.infra.config.app;

import org.project.spring.tax_office.logic.entity.user.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class UserControllerConfig {

    @Bean
    public Map<UserRole, String> userHomePageViews() {
        return Map.of(UserRole.INSPECTOR, "/inspector/homePage.jsp", UserRole.CLIENT, "/client/homePage.jsp");
    }
}
