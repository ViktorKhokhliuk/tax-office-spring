package org.project.spring.tax_office.logic.service;

import lombok.RequiredArgsConstructor;
import org.project.spring.tax_office.logic.entity.report.Report;
import org.project.spring.tax_office.logic.repository.report.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    public List<Report> getAll(int page) {
        int index = (page - 1) * 5;
        return reportRepository.getAll(index);
    }

    public List<Report> getClientReports(Long clientId, int page) {
        int index = (page - 1) * 5;
        return reportRepository.getClientReports(clientId, index);
    }

    public double getCountOfPageForAll() {
        return getCountOfPage(reportRepository.getCountOfFieldForAll());
    }

    public double getCountOfPageForClientReports(Long clientId) {
        return getCountOfPage(reportRepository.getCountOfFieldForClientReports(clientId));
    }

    private double getCountOfPage(double countOfField) {
        return Math.ceil(countOfField / 5);
    }

}
