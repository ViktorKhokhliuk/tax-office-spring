package org.project.spring.tax_office.infra.web.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Log4j2
@ControllerAdvice
public class TaxOfficeControllerAdvice {

    @ExceptionHandler(TaxOfficeException.class)
    public ModelAndView taxOfficeExceptionHandling(Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/error/exception.jsp");
        modelAndView.addObject("message", exception.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView runtimeExceptionHandling(Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/error/internalError.jsp");
        modelAndView.addObject("message", exception.getMessage());
        return modelAndView;
    }
}
