package org.project.spring.tax_office.logic.service;

import lombok.RequiredArgsConstructor;
import org.project.spring.tax_office.logic.entity.dto.ClientReportFilterDto;
import org.project.spring.tax_office.logic.entity.dto.ReportFilterDto;
import org.project.spring.tax_office.logic.entity.dto.ReportUpdateDto;
import org.project.spring.tax_office.logic.entity.report.Report;
import org.project.spring.tax_office.logic.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportUpdateDto updateReportStatus(ReportUpdateDto dto) {
        return reportRepository.updateReportStatus(dto);
    }

    public int deleteReportById(Long id) {
        return reportRepository.deleteReportById(id);
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

    private double getCountOfPages(double countOfFields) {
        return Math.ceil(countOfFields / 5);
    }

    private int getIndex(int page) {
        return (page - 1) * 5;
    }
}
