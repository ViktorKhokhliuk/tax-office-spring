package org.project.spring.tax_office.infra.config.db;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:app.properties")
public class LiquibaseConfig {

    @Value("${change_log_file}")
    private String change_log_file;

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog(change_log_file);
        liquibase.setDataSource(dataSource);
        return liquibase;
    }
}
