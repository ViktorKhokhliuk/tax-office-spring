package org.project.spring.tax_office.logic.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.project.spring.tax_office.logic.entity.report.ReportData;
import org.project.spring.tax_office.infra.web.exception.ReportException;
import org.springframework.stereotype.Component;

import java.io.File;

@Log4j2
@Component(".json")
@RequiredArgsConstructor
public class JsonParser implements Parser {
    private final ObjectMapper objectMapper;
    @Override
    public ReportData parse(File jsonFile) {
        try {
            ReportData reportData = objectMapper.readValue(jsonFile, ReportData.class);
            FileUtils.deleteQuietly(jsonFile);
            return reportData;
        } catch (Exception e) {
            FileUtils.deleteQuietly(jsonFile);
            log.error("cannot parse json file", e);
            throw new ReportException("invalid file");
        }
    }
}
