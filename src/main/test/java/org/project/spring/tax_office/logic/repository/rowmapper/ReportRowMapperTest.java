package org.project.spring.tax_office.logic.repository.rowmapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.project.spring.tax_office.logic.entity.report.Report;
import org.project.spring.tax_office.logic.entity.report.ReportInfo;
import org.project.spring.tax_office.logic.entity.report.ReportStatus;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReportRowMapperTest {
    @InjectMocks
    private ReportRowMapper reportRowMapper;


    private static final Long ID = 1L;
    private static final String TITLE = "report.xml";
    private static final LocalDate DATE = LocalDate.now();
    private static final String TYPE = "income tax";
    private static final String STATUS = ReportStatus.ACCEPTED.getTitle();
    private static final String INFO = ReportInfo.ACCEPT.getTitle();
    private static final Long CLIENT_ID = 1L;

    @Test
    public void mapRow() throws SQLException {
        Report expectedReport = new Report(ID, TITLE, DATE, TYPE, STATUS, INFO, CLIENT_ID);
        ResultSet resultSet = mock(ResultSet.class);

        when(resultSet.getLong("id")).thenReturn(ID);
        when(resultSet.getString("title")).thenReturn(TITLE);
        when(resultSet.getDate("date")).thenReturn(Date.valueOf(DATE));
        when(resultSet.getString("type")).thenReturn(TYPE);
        when(resultSet.getString("status")).thenReturn(STATUS);
        when(resultSet.getString("info")).thenReturn(INFO);
        when(resultSet.getLong("client_id")).thenReturn(CLIENT_ID);

        Report resultReport = reportRowMapper.mapRow(resultSet, 1);
        assertNotNull(resultReport);
        assertEquals(expectedReport, resultReport);
    }
}
