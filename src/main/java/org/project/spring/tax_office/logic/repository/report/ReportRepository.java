package org.project.spring.tax_office.logic.repository.report;

import lombok.RequiredArgsConstructor;
import org.project.spring.tax_office.logic.entity.report.Report;
import org.project.spring.tax_office.logic.entity.report.ReportData;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReportRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ReportRowMapper reportRowMapper;
    private final ReportDataResultSetExtractor reportDataResultSetExtractor;

    private static final String SELECT_REPORT_BY_CLIENT_ID = "select * from report where clientId = ? limit ?, 5;";
    private static final String SELECT_COUNT_ALL_REPORTS = "select count(*) from report";
    private static final String SELECT_COUNT_REPORTS_BY_CLIENT = "select count(*) from report where clientId = ?;";
    private static final String SELECT_ALL_REPORTS = "select * from report limit ?, 5;";
    private static final String SELECT_REPORT_DATA = "select * from report_data where report_id = ?;";

    public List<Report> getAll(int index) {
        return jdbcTemplate.query(SELECT_ALL_REPORTS, reportRowMapper, index);
    }

    public List<Report> getClientReports(Long clientId, int index) {
        return jdbcTemplate.query(SELECT_REPORT_BY_CLIENT_ID, reportRowMapper, clientId, index);
    }

    public Optional<ReportData> getReportData(Long reportId) {
        return jdbcTemplate.query(SELECT_REPORT_DATA, reportDataResultSetExtractor, reportId);
    }

    public Double getCountOfFieldForAll() {
        return jdbcTemplate.queryForObject(SELECT_COUNT_ALL_REPORTS, Double.class);
    }

    public Double getCountOfFieldForClientReports(Long clientId) {
        return jdbcTemplate.queryForObject(SELECT_COUNT_REPORTS_BY_CLIENT, Double.class, clientId);
    }
}
