package org.project.spring.tax_office.logic.repository.rowmapper;

import org.project.spring.tax_office.logic.entity.report.Report;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Component
public class ReportRowMapper implements RowMapper<Report> {
    @Override
    public Report mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        long id = resultSet.getLong("id");
        String title = resultSet.getString("title");
        LocalDate date = resultSet.getDate("date").toLocalDate();
        String type = resultSet.getString("type");
        String status = resultSet.getString("status");
        String info = resultSet.getString("info");
        long clientId = resultSet.getLong("client_id");
        return new Report(id, title, date, type, status, info, clientId);
    }
}
