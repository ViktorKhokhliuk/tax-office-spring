package org.project.spring.tax_office.logic.repository.extractor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.project.spring.tax_office.logic.entity.report.ReportData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReportDataResultSetExtractorTest {
    @InjectMocks
    private ReportDataResultSetExtractor extractor;

    private static final Long ID = 1L;
    private static final String PERSON = "natural";
    private static final String NATIONALITY = "ukrainian";
    private static final String TAX_YEAR = "2022";
    private static final Integer QUARTER = 2;
    private static final Integer MONTH_NUMBER = 10;
    private static final String TAX_GROUP = "IV";
    private static final String ACTIVITY = "Programmer";
    private static final String INCOME = "10000";

    @Test
    public void extractData() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        ReportData expectedReportData = new ReportData(ID,PERSON,NATIONALITY,TAX_YEAR,QUARTER,MONTH_NUMBER,TAX_GROUP,ACTIVITY,INCOME);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(ID);
        when(resultSet.getString("person")).thenReturn(PERSON);
        when(resultSet.getString("nationality")).thenReturn(NATIONALITY);
        when(resultSet.getString("tax_year")).thenReturn(TAX_YEAR);
        when(resultSet.getInt("quarter")).thenReturn(QUARTER);
        when(resultSet.getInt("month_number")).thenReturn(MONTH_NUMBER);
        when(resultSet.getString("tax_group")).thenReturn(TAX_GROUP);
        when(resultSet.getString("activity")).thenReturn(ACTIVITY);
        when(resultSet.getString("income")).thenReturn(INCOME);

        Optional<ReportData> resultReportData = extractor.extractData(resultSet);

        assertNotNull(resultReportData);
        assertTrue(resultReportData.isPresent());
        assertEquals(expectedReportData, resultReportData.get());

        verify(resultSet).next();
    }

    @Test
    public void extractDataWhenReturnOptionalEmpty() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(false);

        Optional<ReportData> resultReportData = extractor.extractData(resultSet);

        assertNotNull(resultReportData);
        assertFalse(resultReportData.isPresent());
    }
}
