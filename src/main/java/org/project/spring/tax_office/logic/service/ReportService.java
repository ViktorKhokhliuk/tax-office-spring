package org.project.spring.tax_office.logic.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.project.spring.tax_office.logic.entity.dto.ClientReportFilterDto;
import org.project.spring.tax_office.logic.entity.dto.ReportCreateDto;
import org.project.spring.tax_office.logic.entity.dto.ReportFilterDto;
import org.project.spring.tax_office.logic.entity.dto.ReportUpdateDto;
import org.project.spring.tax_office.logic.entity.report.Report;
import org.project.spring.tax_office.logic.entity.report.ReportData;
import org.project.spring.tax_office.logic.exception.ReportException;
import org.project.spring.tax_office.logic.parser.Parser;
import org.project.spring.tax_office.logic.repository.report.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final Map<String, Parser> parsers;

    @Transactional
    public Report uploadReport(ReportCreateDto reportCreateDto, File file) {
        String fileExtension = getFileExtension(reportCreateDto.getTitle());

        ReportData reportData = parsers
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(fileExtension))
                .findFirst()
                .orElseThrow(() -> {
                    FileUtils.deleteQuietly(file);
                    throw new ReportException("Please choose allowed file type: XML or JSON");
                })
                .getValue()
                .parse(file);

        return reportRepository.insertReport(reportCreateDto, reportData);
    }

    private String getFileExtension(String fileName) {
        int index = fileName.lastIndexOf(".");
        return fileName.substring(index);
    }

    public List<Report> getAllReports(int page) {
        int index = getIndex(page);
        return reportRepository.getAllReports(index);
    }

    public double getCountOfPagesForAll() {
        return getCountOfPages(reportRepository.getCountOfFieldsForAllReports());
    }

    public List<Report> getAllReportsByFilter(ReportFilterDto dto) {
        int index = getIndex(dto.getPage());
        return reportRepository.getAllReportsByFilter(dto, index);
    }

    public double getCountOfPagesForAllReportsByFilter(ReportFilterDto dto) {
        Double countOfFields = reportRepository.getCountOfFieldsForAllReportsByFilter(dto);
        return getCountOfPages(countOfFields);
    }

    public List<Report> getClientReports(Long clientId, int page) {
        int index = getIndex(page);
        return reportRepository.getClientReports(clientId, index);
    }

    public double getCountOfPagesForClientReports(Long clientId) {
        return getCountOfPages(reportRepository.getCountOfFieldsForClientReports(clientId));
    }

    public List<Report> getClientReportsByFilter(ClientReportFilterDto dto) {
        int index = getIndex(dto.getPage());
        return reportRepository.getClientReportsByFilter(dto, index);
    }

    public double getCountOfPagesForClientReportsByFilter(ClientReportFilterDto dto) {
        return getCountOfPages(reportRepository.getCountOfFieldsForClientReportsByFilter(dto));
    }

    public ReportData getReportData(Long reportId) {
        return reportRepository.getReportData(reportId).orElseThrow(() -> new ReportException("cannot find report data"));
    }

    public ReportData editReportData(ReportData editedReportData) {
        return reportRepository.updateReportData(editedReportData);
    }
    public ReportUpdateDto updateReportStatus(ReportUpdateDto dto) {
        return reportRepository.updateReportStatus(dto);
    }
    private double getCountOfPages(double countOfField) {
        return Math.ceil(countOfField / 5);
    }

    private int getIndex(int page) {
        return (page - 1) * 5;
    }

}
