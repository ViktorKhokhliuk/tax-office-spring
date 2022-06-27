package org.project.spring.tax_office.infra.web.exception;

import org.project.spring.tax_office.infra.web.exception.TaxOfficeException;

public class ReportException extends TaxOfficeException {
    public ReportException(String message) {
        super(message);
    }
}
