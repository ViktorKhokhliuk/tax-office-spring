package org.project.spring.tax_office.logic.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.project.spring.tax_office.logic.entity.dto.ClientReportFilterDto;
import org.project.spring.tax_office.logic.entity.dto.ReportFilterDto;
import org.project.spring.tax_office.logic.entity.dto.ReportUpdateDto;
import org.project.spring.tax_office.logic.entity.report.Report;
import org.project.spring.tax_office.logic.repository.rowmapper.ReportRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReportRepositoryTest {
    @Mock
    private JdbcTemplate jdbcTemplate;
    @Mock
    private ReportRowMapper reportRowMapper;
    @InjectMocks
    private ReportRepository reportRepository;

    private static final String SELECT_ALL_REPORTS = "select * from report client_id limit ?, 5;";
    private static final String SELECT_COUNT_FOR_ALL_REPORTS = "select count(*) from report";

    private static final String SELECT_REPORTS_BY_CLIENT_ID = "select * from report where client_id = ? limit ?, 5;";
    private static final String SELECT_COUNT_FOR_REPORTS_BY_CLIENT = "select count(*) from report where client_id = ?;";

    private static final String UPDATE_REPORT_STATUS = "update report set status = ?, info = ? where id = ?;";
    private static final String DELETE_REPORT_BY_ID = "delete from report where id = ?";

    private static final String SELECT_ALL_REPORTS_BY_FILTER = """
            select report.id, title, date, type, status, info, client_id
             from report join client on report.client_id=client.id
             where status like concat(?,'%') and type like concat(?,'%')
             and date like concat('%',?,'%') and name like concat('%',?,'%')
             and surname like concat('%',?,'%') and tin like concat('%',?,'%') limit ?, 5;
            """;
    private static final String SELECT_COUNT_FOR_ALL_REPORTS_BY_FILTER = """
            select count(*) from report join client on report.client_id=client.id
             where status like concat(?,'%') and type like concat(?,'%')
             and date like concat('%',?,'%') and name like concat('%',?,'%')
             and surname like concat('%',?,'%') and tin like concat('%',?,'%');
            """;
    private static final String SELECT_CLIENT_REPORTS_BY_FILTER = """
            select * from report where status like concat(?,'%')
             and type like concat(?,'%') and date like concat('%',?,'%')
             and client_id = ? limit ?, 5;
            """;
    private static final String SELECT_COUNT_FOR_CLIENT_REPORTS_BY_FILTER = """
            select count(*) from report where status like concat(?,'%')
             and type like concat(?,'%') and date like concat('%',?,'%')
             and client_id = ?;
            """;

    @Test
    public void updateReportStatus() {
        ReportUpdateDto dto = mock(ReportUpdateDto.class);
        when(jdbcTemplate.update(UPDATE_REPORT_STATUS, dto.getUpdatedStatus(), dto.getInfo(), dto.getReportId())).thenReturn(1);

        ReportUpdateDto resultDto = reportRepository.updateReportStatus(dto);
        assertNotNull(resultDto);
        assertEquals(dto, resultDto);
    }

    @Test
    public void deleteReportById() {
        int expected = 1;
        long id = 0L;
        when(jdbcTemplate.update(DELETE_REPORT_BY_ID, id)).thenReturn(expected);

        int result = reportRepository.deleteReportById(id);
        assertTrue(result > 0);
        assertEquals(expected, result);
    }

    @Test
    public void getAllReports() {
        List<Report> expectedReports = List.of(mock(Report.class), mock(Report.class));
        int index = 0;

        when(jdbcTemplate.query(SELECT_ALL_REPORTS, reportRowMapper, index)).thenReturn(expectedReports);

        List<Report> resultReports = reportRepository.getAllReports(index);
        assertNotNull(resultReports);
        assertFalse(resultReports.isEmpty());
        assertEquals(expectedReports, resultReports);
    }

    @Test
    public void getCountOfFieldsForAllReports() {
        double expected = 3.0;
        when(jdbcTemplate.queryForObject(SELECT_COUNT_FOR_ALL_REPORTS, Double.class)).thenReturn(expected);

        double result = reportRepository.getCountOfFieldsForAllReports();
        assertTrue(result > 0);
        assertEquals(expected, result, 0.0);
    }

    @Test
    public void getAllReportsByFilter() {
        ReportFilterDto dto = mock(ReportFilterDto.class);
        int index = 0;

        List<Report> expectedReports = List.of(mock(Report.class), mock(Report.class));
        when(jdbcTemplate.query(SELECT_ALL_REPORTS_BY_FILTER, reportRowMapper,
                dto.getStatus(), dto.getType(), dto.getDate(), dto.getName(), dto.getSurname(), dto.getTin(), index))
                .thenReturn(expectedReports);

        List<Report> resultReports = reportRepository.getAllReportsByFilter(dto, index);
        assertNotNull(resultReports);
        assertFalse(resultReports.isEmpty());
        assertEquals(expectedReports, resultReports);
    }

    @Test
    public void getCountOfFieldsForAllReportsByFilter() {
        ReportFilterDto dto = mock(ReportFilterDto.class);
        double expected = 3.0;
        when(jdbcTemplate.queryForObject(SELECT_COUNT_FOR_ALL_REPORTS_BY_FILTER, Double.class,
                dto.getStatus(), dto.getType(), dto.getDate(), dto.getName(), dto.getSurname(), dto.getTin()))
                .thenReturn(expected);

        double result = reportRepository.getCountOfFieldsForAllReportsByFilter(dto);
        assertTrue(result > 0);
        assertEquals(expected, result, 0.0);
    }

    @Test
    public void getClientReports() {
        int index = 0;
        long clientId = 0L;
        List<Report> expectedReports = List.of(mock(Report.class), mock(Report.class));

        when(jdbcTemplate.query(SELECT_REPORTS_BY_CLIENT_ID, reportRowMapper, clientId, index)).thenReturn(expectedReports);

        List<Report> resultReports = reportRepository.getClientReports(clientId, index);
        assertNotNull(resultReports);
        assertFalse(resultReports.isEmpty());
        assertEquals(expectedReports, resultReports);
    }

    @Test
    public void getCountOfFieldsForClientReports() {
        long clientId = 0L;
        double expected = 3.0;
        when(jdbcTemplate.queryForObject(SELECT_COUNT_FOR_REPORTS_BY_CLIENT, Double.class, clientId)).thenReturn(expected);

        double result = reportRepository.getCountOfFieldsForClientReports(clientId);
        assertTrue(result > 0);
        assertEquals(expected, result, 0.0);
    }

    @Test
    public void getClientReportsByFilter() {
        int index = 0;
        ClientReportFilterDto dto = mock(ClientReportFilterDto.class);
        List<Report> expectedReports = List.of(mock(Report.class), mock(Report.class));

        when(jdbcTemplate.query(SELECT_CLIENT_REPORTS_BY_FILTER, reportRowMapper,
                dto.getStatus(), dto.getType(), dto.getDate(), dto.getClientId(), index))
                .thenReturn(expectedReports);

        List<Report> resultReports = reportRepository.getClientReportsByFilter(dto, index);
        assertNotNull(resultReports);
        assertFalse(resultReports.isEmpty());
        assertEquals(expectedReports, resultReports);
    }

    @Test
    public void getCountOfFieldsForClientReportsByFilter() {
        double expected = 3.0;
        ClientReportFilterDto dto = mock(ClientReportFilterDto.class);

        when(jdbcTemplate.queryForObject(SELECT_COUNT_FOR_CLIENT_REPORTS_BY_FILTER,
                Double.class, dto.getStatus(), dto.getType(), dto.getDate(), dto.getClientId()))
                .thenReturn(expected);

        double result = reportRepository.getCountOfFieldsForClientReportsByFilter(dto);
        assertTrue(result > 0);
        assertEquals(expected, result, 0.0);
    }
}
