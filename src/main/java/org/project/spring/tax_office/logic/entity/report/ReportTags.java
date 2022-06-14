package org.project.spring.tax_office.logic.entity.report;

public enum ReportTags {

    REPORT("report"),
    PERSON("person"),
    NATIONALITY("nationality"),
    YEAR("taxYear"),
    QUARTER("quarter"),
    MONTH("monthNumber"),
    GROUP("taxGroup"),
    ACTIVITY("activity"),
    INCOME("income");

    private final String parameter;

    ReportTags(String value) {
        this.parameter = value;
    }

    public String getValue() {
        return parameter;
    }
}
