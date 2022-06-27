package org.project.spring.tax_office.logic.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.project.spring.tax_office.logic.entity.dto.ReportCreateDto;
import org.project.spring.tax_office.logic.entity.report.Report;
import org.project.spring.tax_office.logic.entity.report.ReportData;
import org.project.spring.tax_office.infra.web.exception.ReportException;
import org.project.spring.tax_office.logic.parser.Parser;
import org.project.spring.tax_office.logic.repository.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportUploadService {

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
}
