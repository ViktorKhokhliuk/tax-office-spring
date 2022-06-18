package org.project.spring.tax_office.logic.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.project.spring.tax_office.logic.entity.report.ReportData;
import org.project.spring.tax_office.logic.exception.ReportException;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class JsonParserTest {

    private final JsonParser jsonParser = new JsonParser(new ObjectMapper());

    private static final File validFile = new File("src/main/test/resources/validJsonTest.json");
    private static final File invalidFile = new File("src/main/test/resources/invalidJsonTest.json");

    private static final ReportData expectedReportData = new ReportData(null, "natural", "ukrainian", "2022",
            2, 10, "IV", "Programmer", "10000");

    @Test
    public void parseWhenValidFile() {

        ReportData resultReportData = jsonParser.parse(validFile);
        assertEquals(expectedReportData, resultReportData);
    }

    @Test(expected = ReportException.class)
    public void parseWhenInvalidFile() {
        jsonParser.parse(invalidFile);
    }
}
