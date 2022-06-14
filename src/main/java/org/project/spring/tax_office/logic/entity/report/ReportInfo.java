package org.project.spring.tax_office.logic.entity.report;

public enum ReportInfo {

    PROCESS("Report in processing"),
    ACCEPT("Report accepted"),
    EDIT("Report edited");

    private final String title;

    ReportInfo(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
