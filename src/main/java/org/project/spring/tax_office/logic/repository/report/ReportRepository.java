package org.project.spring.tax_office.logic.repository.report;

import lombok.RequiredArgsConstructor;
import org.project.spring.tax_office.logic.entity.dto.ReportCreateDto;
import org.project.spring.tax_office.logic.entity.report.Report;
import org.project.spring.tax_office.logic.entity.report.ReportData;
import org.project.spring.tax_office.logic.entity.report.ReportInfo;
import org.project.spring.tax_office.logic.entity.report.ReportStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReportRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ReportRowMapper reportRowMapper;
    private final ReportDataResultSetExtractor reportDataResultSetExtractor;

    private static final String INSERT_REPORT = """
                                insert into report
                                (title, date, type, status, info, client_id)
                                values (?,?,?,?,?,?);
                                """;
    private static final String INSERT_REPORT_DATA = """
                                insert into report_data
                                (id, person, nationality, tax_year, quarter, month_number, tax_group, activity, income)
                                values (?,?,?,?,?,?,?,?,?);
                                """;
    private static final String SELECT_REPORT_BY_CLIENT_ID = "select * from report where client_id = ? limit ?, 5;";
    private static final String SELECT_COUNT_ALL_REPORTS = "select count(*) from report";
    private static final String SELECT_COUNT_REPORTS_BY_CLIENT = "select count(*) from report where client_id = ?;";
    private static final String SELECT_ALL_REPORTS = "select * from report limit ?, 5;";
    private static final String SELECT_REPORT_DATA = "select * from report_data where id = ?;";

    public Report insertReport(ReportCreateDto dto, ReportData reportData) {
//        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("report");
//        KeyHolder keyHolder1 = simpleJdbcInsert.executeAndReturnKeyHolder(new HashMap<>());
//        simpleJdbcInsert.withTableName("report_data");
//        simpleJdbcInsert.execute(new HashMap<>());
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_REPORT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, dto.getTitle());
            preparedStatement.setDate(2, Date.valueOf(LocalDate.now()));
            preparedStatement.setString(3, dto.getType());
            preparedStatement.setString(4, ReportStatus.SUBMITTED.getTitle());
            preparedStatement.setString(5, ReportInfo.PROCESS.getTitle());
            preparedStatement.setLong(6, dto.getClientId());
            return preparedStatement;
        }, keyHolder);

        long reportId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_REPORT_DATA);
                preparedStatement.setLong(1, reportId);
                preparedStatement.setString(2, reportData.getPerson());
                preparedStatement.setString(3, reportData.getNationality());
                preparedStatement.setString(4, reportData.getTaxYear());
                preparedStatement.setInt(5, reportData.getQuarter());
                preparedStatement.setInt(6, reportData.getMonthNumber());
                preparedStatement.setString(7, reportData.getGroup());
                preparedStatement.setString(8, reportData.getActivity());
                preparedStatement.setString(9, reportData.getIncome());
                return preparedStatement;
            });
        } catch (Exception e) {
            e.printStackTrace();
    }

        return new Report(reportId, dto.getTitle(), LocalDate.now(), dto.getType(), ReportStatus.SUBMITTED.getTitle(), ReportInfo.PROCESS.getTitle(), dto.getClientId());
    }

    public List<Report> getAll(int index) {
        return jdbcTemplate.query(SELECT_ALL_REPORTS, reportRowMapper, index);
    }

    public List<Report> getClientReports(Long clientId, int index) {
        return jdbcTemplate.query(SELECT_REPORT_BY_CLIENT_ID, reportRowMapper, clientId, index);
    }

    public Optional<ReportData> getReportData(Long reportId) {
        return jdbcTemplate.query(SELECT_REPORT_DATA, reportDataResultSetExtractor, reportId);
    }

    public Double getCountOfFieldsForAll() {
        return jdbcTemplate.queryForObject(SELECT_COUNT_ALL_REPORTS, Double.class);
    }

    public Double getCountOfFieldsForClientReports(Long clientId) {
        return jdbcTemplate.queryForObject(SELECT_COUNT_REPORTS_BY_CLIENT, Double.class, clientId);
    }
}
