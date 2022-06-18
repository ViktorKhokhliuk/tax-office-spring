package org.project.spring.tax_office.logic.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.project.spring.tax_office.logic.entity.report.ReportData;
import org.project.spring.tax_office.logic.exception.ReportException;
import org.project.spring.tax_office.logic.repository.ReportDataRepository;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReportDataServiceTest {

    @Mock
    private ReportDataRepository reportDataRepository;
    @InjectMocks
    private ReportDataService reportDataService;

    @Test
    public void editReportData() {
        ReportData expectedReportData = mock(ReportData.class);
        int expected = 1;
        when(reportDataRepository.updateReportData(expectedReportData)).thenReturn(expected);

        int result = reportDataService.editReportData(expectedReportData);
        assertTrue(result > 0);
        assertEquals(expected, result);

        verify(reportDataRepository).updateReportData(expectedReportData);
    }

    @Test
    public void getReportData() {
        ReportData expectedReportData = mock(ReportData.class);
        when(reportDataRepository.getReportData(anyLong())).thenReturn(Optional.of(expectedReportData));

        ReportData resultReportData = reportDataService.getReportData(anyLong());
        assertEquals(expectedReportData, resultReportData);

        verify(reportDataRepository).getReportData(anyLong());
    }

    @Test(expected = ReportException.class)
    public void getReportDataThrowExceptionWhenNotFoundReportData() {
        when(reportDataRepository.getReportData(anyLong())).thenReturn(Optional.empty());
        reportDataService.getReportData(anyLong());
    }
}
