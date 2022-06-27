package org.project.spring.tax_office.logic.parser;

import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.project.spring.tax_office.logic.entity.report.ReportData;
import org.project.spring.tax_office.infra.web.exception.ReportException;

import javax.xml.parsers.DocumentBuilderFactory;

import java.io.File;

import static org.junit.Assert.assertEquals;

@RequiredArgsConstructor
public class XmlParserTest {

    private final XmlParser xmlParser = new XmlParser(DocumentBuilderFactory.newInstance());

    private static final File validFile = new File("src/main/test/resources/validXmlTest.xml");
    private static final File invalidFile = new File("src/main/test/resources/invalidXmlTest.xml");

    private static final ReportData expectedReportData = new ReportData(null, "natural", "ukrainian", "2022",
            2, 10, "IV", "Programmer", "10000");

    @Test
    public void parseWhenValidFile() {

        ReportData resultReportData = xmlParser.parse(validFile);
        assertEquals(expectedReportData, resultReportData);
    }

    @Test(expected = ReportException.class)
    public void parseWhenInvalidFile() {
        xmlParser.parse(invalidFile);
    }
}
