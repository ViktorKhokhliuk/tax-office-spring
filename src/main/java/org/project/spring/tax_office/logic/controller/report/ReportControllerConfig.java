package org.project.spring.tax_office.logic.controller.report;

import org.project.spring.tax_office.logic.entity.user.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ComponentScan("org.project.spring.tax_office.logic.controller.report")
public class ReportControllerConfig {

    @Bean
    public Map<UserRole, String> views() {
        return Map.of(UserRole.INSPECTOR, "/tax-office/inspector/clientReports.jsp", UserRole.CLIENT, "/tax-office/client/reports.jsp");
    }
}
