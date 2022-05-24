package org.project.spring.tax_office.infra.web.exception;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Log4j2
@ControllerAdvice
public class TaxOfficeAdvice {

    @ExceptionHandler(TaxOfficeException.class)
    public ModelAndView taxOfficeExceptionHandling(HttpServletRequest request, Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/error/exception.jsp");
        modelAndView.addObject("message", exception.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView RuntimeExceptionHandling(HttpServletRequest request, Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/error/internalError.jsp");
        modelAndView.addObject("message", exception.getMessage());
        return modelAndView;
    }
}
