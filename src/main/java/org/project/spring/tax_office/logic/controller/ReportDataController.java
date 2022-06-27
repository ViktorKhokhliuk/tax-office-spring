package org.project.spring.tax_office.logic.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.project.spring.tax_office.infra.web.QueryParameterResolver;
import org.project.spring.tax_office.logic.entity.report.ReportData;
import org.project.spring.tax_office.logic.service.ReportDataService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
@Log4j2
@Controller
@RequestMapping("/report/data")
@RequiredArgsConstructor
public class ReportDataController {

    private final ReportDataService reportDataService;
    private final QueryParameterResolver queryParameterResolver;

    @GetMapping()
    public ModelAndView getReportData(@RequestParam("id") Long reportId) {
        ModelAndView modelAndView = new ModelAndView("/user/report.jsp");
        modelAndView.addObject("reportData", reportDataService.getReportData(reportId));
        return modelAndView;
    }

    @GetMapping("/edit")
    public ModelAndView getReportDataForEdit(@RequestParam("id") Long reportId, @ModelAttribute("message") String message) {
        ModelAndView modelAndView = new ModelAndView("/client/edit.jsp");
        modelAndView.addObject("reportData", reportDataService.getReportData(reportId));
        modelAndView.addObject("message", message);
        return modelAndView;
    }

    @PostMapping("/edit")
    public RedirectView editReportData(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        ReportData editedReportData = queryParameterResolver.getObject(request, ReportData.class);
        reportDataService.editReportData(editedReportData);
        redirectAttributes.addAttribute("id", editedReportData.getId());
        redirectAttributes.addFlashAttribute("message", "Report has been edited successfully!");
        log.log(Level.INFO, "Report has been edited successfully!");
        return new RedirectView("/tax-office/service/report/data/edit");
    }
}
