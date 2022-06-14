package org.project.spring.tax_office.logic.controller.report;

import org.project.spring.tax_office.logic.entity.user.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ReportControllerConfig {

    @Bean
    public Map<UserRole, String> clientReportsViews() {
        return Map.of(UserRole.INSPECTOR, "/inspector/clientReports.jsp", UserRole.CLIENT, "/client/reports.jsp");
    }
}
