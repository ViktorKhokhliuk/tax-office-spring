package org.project.spring.tax_office.logic.service;

import lombok.RequiredArgsConstructor;
import org.project.spring.tax_office.logic.entity.report.ReportData;
import org.project.spring.tax_office.infra.web.exception.ReportException;
import org.project.spring.tax_office.logic.repository.ReportDataRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportDataService {

    private final ReportDataRepository reportDataRepository;

    public int editReportData(ReportData editedReportData) {
        return reportDataRepository.updateReportData(editedReportData);
    }

    public ReportData getReportData(Long reportId) {
        return reportDataRepository.getReportData(reportId).orElseThrow(() -> new ReportException("cannot find report data"));
    }
}
