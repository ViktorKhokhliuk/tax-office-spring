package org.project.spring.tax_office.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.project.spring.tax_office.infra.web.exception.TaxOfficeControllerAdvice;
import org.project.spring.tax_office.infra.web.exception.ReportException;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TaxOfficeControllerAdviceTest {

    @InjectMocks
    private TaxOfficeControllerAdvice controllerAdvice;

    private static final String MESSAGE = "some message";

    @Test
    public void taxOfficeExceptionHandling() {
        ModelAndView modelAndView = controllerAdvice.taxOfficeExceptionHandling(new ReportException(MESSAGE));
        assertEquals("/error/exception.jsp", modelAndView.getViewName());
        assertEquals(MESSAGE, modelAndView.getModel().get("message"));
    }

    @Test
    public void runtimeExceptionHandling() {
        ModelAndView modelAndView = controllerAdvice.runtimeExceptionHandling(new RuntimeException(MESSAGE));
        assertEquals("/error/internalError.jsp", modelAndView.getViewName());
        assertEquals(MESSAGE, modelAndView.getModel().get("message"));
    }
}
