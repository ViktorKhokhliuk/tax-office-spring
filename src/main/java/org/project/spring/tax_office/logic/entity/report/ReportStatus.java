package org.project.spring.tax_office.logic.entity.report;

public enum ReportStatus {

    SUBMITTED("SUBMITTED"),
    ACCEPTED("ACCEPTED"),
    EDITED("EDITED");

    private final String title;

    ReportStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
