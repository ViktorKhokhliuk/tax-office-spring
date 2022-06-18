package org.project.spring.tax_office.logic.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.project.spring.tax_office.logic.entity.dto.ReportCreateDto;
import org.project.spring.tax_office.logic.entity.report.Report;
import org.project.spring.tax_office.logic.entity.report.ReportData;
import org.project.spring.tax_office.logic.exception.ReportException;
import org.project.spring.tax_office.logic.parser.Parser;
import org.project.spring.tax_office.logic.repository.ReportRepository;

import java.io.File;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReportUploadServiceTest {

    private final ReportRepository reportRepository = mock(ReportRepository.class);

    private final Map<String, Parser> parsers = Map.of(".xml", mock(Parser.class));

    private final ReportUploadService reportUploadService = new ReportUploadService(reportRepository, parsers);

    private static final String VALID_TITLE = "report.xml";
    private static final String INVALID_TITLE = "report.txt";

    @Test
    public void uploadReport() {
        File file = mock(File.class);
        ReportCreateDto dto = mock(ReportCreateDto.class);
        ReportData reportData = mock(ReportData.class);
        Report expectedReport = mock(Report.class);

        when(dto.getTitle()).thenReturn(VALID_TITLE);
        when(parsers.get(".xml").parse(file)).thenReturn(reportData);
        when(reportRepository.insertReport(dto, reportData)).thenReturn(expectedReport);

        Report resultReport = reportUploadService.uploadReport(dto, file);

        assertEquals(expectedReport, resultReport);

        verify(parsers.get(".xml")).parse(file);
        verify(reportRepository).insertReport(dto, reportData);
    }

    @Test(expected = ReportException.class)
    public void uploadReportThrowExceptionWhenWrongFileExtension() {
        File file = mock(File.class);
        ReportCreateDto dto = mock(ReportCreateDto.class);
        when(dto.getTitle()).thenReturn(INVALID_TITLE);
        reportUploadService.uploadReport(dto, file);
    }
}
