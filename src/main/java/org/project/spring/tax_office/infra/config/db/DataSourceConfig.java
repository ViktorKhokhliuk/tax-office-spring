package org.project.spring.tax_office.infra.config.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:app.properties")
public class DataSourceConfig {

    @Value("${mysql_jdbc_Driver}")
    private String driver;
    @Value("${jdbc_url}")
    private String url;
    @Value("${user_name}")
    private String userName;
    @Value("${password}")
    private String userPassword;

    @Bean
    public DataSource dataSource() {

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(driver);
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(userName);
        hikariConfig.setPassword(userPassword);
        return new HikariDataSource(hikariConfig);
    }
}
