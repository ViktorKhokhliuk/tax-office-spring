package org.project.spring.tax_office.logic.parser;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.project.spring.tax_office.logic.entity.report.ReportData;
import org.project.spring.tax_office.logic.entity.report.ReportTags;
import org.project.spring.tax_office.logic.exception.ReportException;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

@Log4j2
@Component(".xml")
@RequiredArgsConstructor
public class XmlParser implements Parser {
    private final DocumentBuilderFactory factory;

    @Override
    public ReportData parse(File xmlFile) {
        try {
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile.getAbsolutePath());
            Element root = document.getDocumentElement();
            ReportData reportData = buildReportData(root);
            FileUtils.deleteQuietly(xmlFile);
            return reportData;
        } catch (Exception e) {
            FileUtils.deleteQuietly(xmlFile);
            log.error("cannot parse xml file",e);
            throw new ReportException("invalid file");
        }
    }

    private ReportData buildReportData(Element root) {
        ReportData reportData = new ReportData();
        reportData.setPerson(getTextContext(root, ReportTags.PERSON.getValue()));
        reportData.setNationality(getTextContext(root, ReportTags.NATIONALITY.getValue()));
        reportData.setTaxYear(getTextContext(root, ReportTags.YEAR.getValue()));
        reportData.setPerson(getTextContext(root, ReportTags.PERSON.getValue()));
        reportData.setQuarter(Integer.valueOf(getTextContext(root, ReportTags.QUARTER.getValue())));
        reportData.setMonthNumber(Integer.valueOf(getTextContext(root, ReportTags.MONTH.getValue())));
        reportData.setGroup(getTextContext(root, ReportTags.GROUP.getValue()));
        reportData.setActivity(getTextContext(root, ReportTags.ACTIVITY.getValue()));
        reportData.setIncome(getTextContext(root, ReportTags.INCOME.getValue()));
        return reportData;
    }

    private String getTextContext(Element root, String value) {
        Node node = root.getElementsByTagName(value).item(0);
        return node.getTextContent();
    }
}
