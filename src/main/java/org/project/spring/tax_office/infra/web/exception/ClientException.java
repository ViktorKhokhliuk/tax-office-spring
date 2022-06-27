package org.project.spring.tax_office.infra.web.exception;

import org.project.spring.tax_office.infra.web.exception.TaxOfficeException;

public class ClientException extends TaxOfficeException {
    public ClientException(String message) {
        super(message);
    }
}
