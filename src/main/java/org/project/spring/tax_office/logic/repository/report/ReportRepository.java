package org.project.spring.tax_office.logic.repository.report;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.project.spring.tax_office.logic.entity.dto.ClientReportFilterDto;
import org.project.spring.tax_office.logic.entity.dto.ReportCreateDto;
import org.project.spring.tax_office.logic.entity.dto.ReportFilterDto;
import org.project.spring.tax_office.logic.entity.dto.ReportUpdateDto;
import org.project.spring.tax_office.logic.entity.report.Report;
import org.project.spring.tax_office.logic.entity.report.ReportData;
import org.project.spring.tax_office.logic.entity.report.ReportInfo;
import org.project.spring.tax_office.logic.entity.report.ReportStatus;
import org.project.spring.tax_office.logic.exception.ReportException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Log4j2
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
    private static final String SELECT_ALL_REPORTS_BY_FILTER = """
            select report.id, title, date, type, status, info, client_id
             from report join client on report.client_id=client.id
             where status like concat('%',?,'%')
             and type like concat('%',?,'%') and date like concat('%',?,'%')
             and name like concat('%',?,'%') and surname like concat('%',?,'%')
             and tin like concat('%',?,'%') limit ?, 5;
            """;
    private static final String SELECT_COUNT_FOR_ALL_REPORTS_BY_FILTER = """
            select count(*) from report join client on report.client_id=client.id
             where status like concat('%',?,'%') and type like concat('%',?,'%')
             and date like concat('%',?,'%') and name like concat('%',?,'%')
             and surname like concat('%',?,'%') and tin like concat('%',?,'%');
            """;
    private static final String SELECT_CLIENT_REPORTS_BY_FILTER = """
            select * from report where status like concat('%',?,'%')
             and type like concat('%',?,'%') and date like concat('%',?,'%')
             and client_id = ? limit ?, 5;
            """;
    private static final String SELECT_COUNT_FOR_CLIENT_REPORTS_BY_FILTER = """
            select count(*) from report where status like concat('%',?,'%')
             and type like concat('%',?,'%') and date like concat('%',?,'%')
             and client_id = ?;
            """;
    private static final String SELECT_ALL_REPORTS = "select * from report client_id limit ?, 5;";
    private static final String SELECT_COUNT_FOR_ALL_REPORTS = "select count(*) from report";
    private static final String SELECT_REPORTS_BY_CLIENT_ID = "select * from report where client_id = ? limit ?, 5;";
    private static final String SELECT_COUNT_FOR_REPORTS_BY_CLIENT = "select count(*) from report where client_id = ?;";
    private static final String SELECT_REPORT_DATA = "select * from report_data where id = ?;";
    private static final String UPDATE_REPORT_AFTER_EDIT = """
            update report_data join report on report_data.id=report.id
             set status = ?, info = ?, person = ?, nationality = ?,
             tax_year = ?, quarter = ?, month_number = ?, tax_group = ?,
             activity = ?, income = ? where report_data.id = ?;
            """;
    private static final String UPDATE_REPORT_STATUS = "update report set status = ?, info = ? where id = ?;";
    public Report insertReport(ReportCreateDto dto, ReportData reportData) {
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
                preparedStatement.setString(7, reportData.getTaxGroup());
                preparedStatement.setString(8, reportData.getActivity());
                preparedStatement.setString(9, reportData.getIncome());
                return preparedStatement;
            });
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ReportException("invalid file");
        }

        return new Report(reportId, dto.getTitle(), LocalDate.now(), dto.getType(), ReportStatus.SUBMITTED.getTitle(), ReportInfo.PROCESS.getTitle(), dto.getClientId());
    }

    public List<Report> getAllReports(int index) {
        return jdbcTemplate.query(SELECT_ALL_REPORTS, reportRowMapper, index);
    }

    public List<Report> getAllReportsByFilter(ReportFilterDto dto, int index) {
        return jdbcTemplate.query(SELECT_ALL_REPORTS_BY_FILTER, reportRowMapper,
                dto.getStatus(), dto.getType(), dto.getDate(), dto.getName(), dto.getSurname(), dto.getTin(), index);
    }

    public List<Report> getClientReports(Long clientId, int index) {
        return jdbcTemplate.query(SELECT_REPORTS_BY_CLIENT_ID, reportRowMapper, clientId, index);
    }

    public List<Report> getClientReportsByFilter(ClientReportFilterDto dto, int index) {
        return jdbcTemplate.query(SELECT_CLIENT_REPORTS_BY_FILTER, reportRowMapper,
                dto.getStatus(), dto.getType(), dto.getDate(), dto.getClientId(), index);
    }

    public Optional<ReportData> getReportData(Long reportId) {
        return jdbcTemplate.query(SELECT_REPORT_DATA, reportDataResultSetExtractor, reportId);
    }

    public ReportData updateReportData(ReportData editedReportData) {
        jdbcTemplate.update(UPDATE_REPORT_AFTER_EDIT, ReportStatus.EDITED.getTitle(), ReportInfo.EDIT.getTitle(),
                editedReportData.getPerson(), editedReportData.getNationality(), editedReportData.getTaxYear(),
                editedReportData.getQuarter(), editedReportData.getMonthNumber(), editedReportData.getTaxGroup(),
                editedReportData.getActivity(), editedReportData.getIncome(), editedReportData.getId());

        return editedReportData;
    }

    public ReportUpdateDto updateReportStatus(ReportUpdateDto dto) {
        jdbcTemplate.update(UPDATE_REPORT_STATUS, dto.getUpdatedStatus(), dto.getInfo(), dto.getReportId());
        return dto;
    }

    public Double getCountOfFieldsForAllReports() {
        return jdbcTemplate.queryForObject(SELECT_COUNT_FOR_ALL_REPORTS, Double.class);
    }

    public Double getCountOfFieldsForAllReportsByFilter(ReportFilterDto dto) {
        return jdbcTemplate.queryForObject(SELECT_COUNT_FOR_ALL_REPORTS_BY_FILTER, Double.class,
                dto.getStatus(), dto.getType(), dto.getDate(), dto.getName(), dto.getSurname(), dto.getTin());
    }

    public Double getCountOfFieldsForClientReports(Long clientId) {
        return jdbcTemplate.queryForObject(SELECT_COUNT_FOR_REPORTS_BY_CLIENT, Double.class, clientId);
    }

    public Double getCountOfFieldsForClientReportsByFilter(ClientReportFilterDto dto) {
        return jdbcTemplate.queryForObject(SELECT_COUNT_FOR_CLIENT_REPORTS_BY_FILTER,
                Double.class, dto.getStatus(), dto.getType(), dto.getDate(), dto.getClientId());
    }
}
