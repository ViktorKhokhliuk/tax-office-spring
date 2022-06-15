package org.project.spring.tax_office.logic.repository.extractor;

import org.project.spring.tax_office.logic.entity.report.ReportData;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class ReportDataResultSetExtractor implements ResultSetExtractor<Optional<ReportData>> {

    @Override
    public Optional<ReportData> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        if (resultSet.next()) {
            long id = resultSet.getLong("id");
            String person = resultSet.getString("person");
            String nationality = resultSet.getString("nationality");
            String year = resultSet.getString("tax_year");
            int quarter = resultSet.getInt("quarter");
            int monthNumber = resultSet.getInt("month_number");
            String group = resultSet.getString("tax_group");
            String activity = resultSet.getString("activity");
            String income = resultSet.getString("income");
            return Optional.of(new ReportData(id, person, nationality, year, quarter, monthNumber, group, activity, income));
        }
        return Optional.empty();
    }
}
