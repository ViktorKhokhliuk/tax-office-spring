package org.project.spring.tax_office.logic.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.project.spring.tax_office.logic.entity.dto.ClientReportFilterDto;
import org.project.spring.tax_office.logic.entity.dto.ReportFilterDto;
import org.project.spring.tax_office.logic.entity.dto.ReportUpdateDto;
import org.project.spring.tax_office.logic.entity.report.Report;
import org.project.spring.tax_office.logic.repository.ReportRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReportServiceTest {

    @Mock
    private ReportRepository reportRepository;
    @InjectMocks
    private ReportService reportService;

    @Test
    public void updateReportStatus() {
        ReportUpdateDto expectedDto = mock(ReportUpdateDto.class);
        when(reportRepository.updateReportStatus(expectedDto)).thenReturn(expectedDto);

        ReportUpdateDto resultDto = reportService.updateReportStatus(expectedDto);
        assertEquals(expectedDto, resultDto);

        verify(reportRepository).updateReportStatus(expectedDto);
    }

    @Test
    public void deleteReportById() {
        Long id = 1L;
        when(reportRepository.deleteReportById(id)).thenReturn(1);

        int result = reportService.deleteReportById(id);
        assertTrue(result > 0);

        verify(reportRepository).deleteReportById(id);
    }

    @Test
    public void getAllReports() {
        List<Report> expectedReports = Arrays.asList(mock(Report.class), mock(Report.class));
        when(reportRepository.getAllReports(anyInt())).thenReturn(expectedReports);

        List<Report> resultReports = reportService.getAllReports(anyInt());
        assertFalse(resultReports.isEmpty());
        assertEquals(expectedReports, resultReports);

        verify(reportRepository).getAllReports(anyInt());
    }

    @Test
    public void getCountOfPagesForAll() {
        double expected = 3.0;
        when(reportRepository.getCountOfFieldsForAllReports()).thenReturn(11.0);

        double result = reportService.getCountOfPagesForAll();
        assertEquals(expected, result, 0.0);

        verify(reportRepository).getCountOfFieldsForAllReports();
    }

    @Test
    public void getAllReportsByFilter() {
        ReportFilterDto dto = mock(ReportFilterDto.class);
        List<Report> expectedReports = Arrays.asList(mock(Report.class), mock(Report.class));
        when(dto.getPage()).thenReturn(1);
        when(reportRepository.getAllReportsByFilter(dto, 0)).thenReturn(expectedReports);

        List<Report> resultReports = reportService.getAllReportsByFilter(dto);
        assertFalse(resultReports.isEmpty());
        assertEquals(expectedReports, resultReports);

        verify(reportRepository).getAllReportsByFilter(dto, 0);
    }

    @Test
    public void getCountOfPagesForAllReportsByFilter() {
        ReportFilterDto dto = mock(ReportFilterDto.class);
        double expected = 3.0;
        when(reportRepository.getCountOfFieldsForAllReportsByFilter(dto)).thenReturn(11.0);

        double result = reportService.getCountOfPagesForAllReportsByFilter(dto);
        assertEquals(expected, result, 0.0);

        verify(reportRepository).getCountOfFieldsForAllReportsByFilter(dto);
    }

    @Test
    public void getClientReports() {
        List<Report> expectedReports = Arrays.asList(mock(Report.class), mock(Report.class));
        when(reportRepository.getClientReports(anyLong(), anyInt())).thenReturn(expectedReports);

        List<Report> resultReports = reportService.getClientReports(anyLong(), anyInt());
        assertFalse(resultReports.isEmpty());
        assertEquals(expectedReports, resultReports);

        verify(reportRepository).getClientReports(anyLong(), anyInt());
    }

    @Test
    public void getCountOfPagesForClientReports() {
        double expected = 3.0;
        when(reportRepository.getCountOfFieldsForClientReports(anyLong())).thenReturn(11.0);

        double result = reportService.getCountOfPagesForClientReports(anyLong());
        assertEquals(expected, result, 0.0);

        verify(reportRepository).getCountOfFieldsForClientReports(anyLong());
    }

    @Test
    public void getClientReportsByFilter() {
        ClientReportFilterDto dto = mock(ClientReportFilterDto.class);
        List<Report> expectedReports = Arrays.asList(mock(Report.class), mock(Report.class));
        when(dto.getPage()).thenReturn(1);
        when(reportRepository.getClientReportsByFilter(dto, 0)).thenReturn(expectedReports);

        List<Report> resultReports = reportService.getClientReportsByFilter(dto);
        assertFalse(resultReports.isEmpty());
        assertEquals(expectedReports, resultReports);

        verify(reportRepository).getClientReportsByFilter(dto, 0);
    }

    @Test
    public void getCountOfPagesForClientReportsByFilter() {
        ClientReportFilterDto dto = mock(ClientReportFilterDto.class);
        double expected = 3.0;
        when(reportRepository.getCountOfFieldsForClientReportsByFilter(dto)).thenReturn(11.0);

        double result = reportService.getCountOfPagesForClientReportsByFilter(dto);
        assertEquals(expected, result, 0.0);

        verify(reportRepository).getCountOfFieldsForClientReportsByFilter(dto);
    }
}
