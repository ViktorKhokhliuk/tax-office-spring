package org.project.spring.tax_office.logic.exception;

import org.project.spring.tax_office.infra.web.exception.TaxOfficeException;

public class UserException extends TaxOfficeException {
    public UserException(String message) {
        super(message);
    }
}
