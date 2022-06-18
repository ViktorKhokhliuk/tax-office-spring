package org.project.spring.tax_office.logic.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.project.spring.tax_office.logic.entity.report.ReportData;
import org.project.spring.tax_office.logic.entity.report.ReportInfo;
import org.project.spring.tax_office.logic.entity.report.ReportStatus;
import org.project.spring.tax_office.logic.repository.extractor.ReportDataResultSetExtractor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReportDataRepositoryTest {
    @Mock
    private JdbcTemplate jdbcTemplate;
    @Mock
    private ReportDataResultSetExtractor reportDataResultSetExtractor;
    @InjectMocks
    private ReportDataRepository reportDataRepository;

    private static final String SELECT_REPORT_DATA = "select * from report_data where id = ?;";
    private static final String UPDATE_REPORT_DATA_AFTER_EDIT = """
            update report_data join report on report_data.id=report.id
             set status = ?, info = ?, person = ?, nationality = ?,
             tax_year = ?, quarter = ?, month_number = ?, tax_group = ?,
             activity = ?, income = ? where report_data.id = ?;
            """;

    @Test
    public void updateReportData() {
        ReportData expectedReportData = mock(ReportData.class);
        int expected = 1;
        when(jdbcTemplate.update(UPDATE_REPORT_DATA_AFTER_EDIT, ReportStatus.EDITED.getTitle(), ReportInfo.EDIT.getTitle(),
                expectedReportData.getPerson(), expectedReportData.getNationality(), expectedReportData.getTaxYear(),
                expectedReportData.getQuarter(), expectedReportData.getMonthNumber(), expectedReportData.getTaxGroup(),
                expectedReportData.getActivity(), expectedReportData.getIncome(), expectedReportData.getId())).thenReturn(expected);

        int result = reportDataRepository.updateReportData(expectedReportData);
        assertTrue(result > 0);
        assertEquals(expected, result);
    }

    @Test
    public void getReportData() {
        long id = 0L;
        ReportData expectedReportData =  mock(ReportData.class);

        when(jdbcTemplate.query(SELECT_REPORT_DATA, reportDataResultSetExtractor, id)).thenReturn(Optional.of(expectedReportData));

        Optional<ReportData> resultReportData = reportDataRepository.getReportData(id);
        assertNotNull(resultReportData);
        assertTrue(resultReportData.isPresent());
        assertEquals(expectedReportData, resultReportData.get());
    }
}
