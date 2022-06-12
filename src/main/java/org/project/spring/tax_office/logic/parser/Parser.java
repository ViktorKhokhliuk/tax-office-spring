package org.project.spring.tax_office.logic.parser;

import org.project.spring.tax_office.logic.entity.report.ReportData;

import java.io.File;

public interface Parser {
    ReportData parse(File file);
}
