package org.project.spring.tax_office.logic.controller;

import lombok.RequiredArgsConstructor;
import org.project.spring.tax_office.infra.web.QueryParameterResolver;
import org.project.spring.tax_office.logic.entity.dto.ClientReportFilterDto;
import org.project.spring.tax_office.logic.entity.dto.ReportDeleteDto;
import org.project.spring.tax_office.logic.entity.dto.ReportFilterDto;
import org.project.spring.tax_office.logic.entity.dto.ReportUpdateDto;
import org.project.spring.tax_office.logic.entity.report.ReportData;
import org.project.spring.tax_office.logic.entity.user.User;
import org.project.spring.tax_office.logic.entity.user.UserRole;
import org.project.spring.tax_office.logic.service.ClientService;
import org.project.spring.tax_office.logic.service.ReportService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final ClientService clientService;
    private final Map<UserRole, String> clientReportsViews;
    private final QueryParameterResolver queryParameterResolver;

    @GetMapping()
    public ModelAndView getAll(@RequestParam("page") int page) {
        ModelAndView modelAndView = new ModelAndView("/inspector/reports.jsp");
        modelAndView.addObject("reports", reportService.getAllReports(page));
        modelAndView.addObject("clients", clientService.getAllClientsNoLimit());
        modelAndView.addObject("countOfPages", reportService.getCountOfPagesForAll());
        modelAndView.addObject("page", page);
        return modelAndView;
    }

    @GetMapping("/filter")
    public ModelAndView getAllByFilter(HttpServletRequest request) {
        ReportFilterDto dto = queryParameterResolver.getObject(request, ReportFilterDto.class);
        ModelAndView modelAndView = new ModelAndView("/inspector/reports.jsp");
        modelAndView.addObject("reports", reportService.getAllReportsByFilter(dto));
        modelAndView.addObject("clients", clientService.getAllClientsNoLimit());
        modelAndView.addObject("countOfPages", reportService.getCountOfPagesForAllReportsByFilter(dto));
        modelAndView.addObject("page", dto.getPage());
        modelAndView.addObject("dto", dto);
        return modelAndView;
    }

    @GetMapping("/client")
    public ModelAndView getClientReports(HttpServletRequest request) {
        User user = (User) request.getSession(false).getAttribute("user");
        int page = Integer.parseInt(request.getParameter("page"));
        Long clientId = Long.valueOf(request.getParameter("clientId"));
        String clientFullName = request.getParameter("clientFullName");
        ModelAndView modelAndView = new ModelAndView(clientReportsViews.get(user.getUserRole()));
        modelAndView.addObject("reports", reportService.getClientReports(clientId, page));
        modelAndView.addObject("countOfPages", reportService.getCountOfPagesForClientReports(clientId));
        modelAndView.addObject("page", page);
        modelAndView.addObject("clientId", clientId);
        modelAndView.addObject("clientFullName", clientFullName);
        return modelAndView;
    }

    @GetMapping("/client/filter")
    public ModelAndView getClientReportsByFilter(HttpServletRequest request) {
        User user = (User) request.getSession(false).getAttribute("user");
        ClientReportFilterDto dto = queryParameterResolver.getObject(request, ClientReportFilterDto.class);
        ModelAndView modelAndView = new ModelAndView(clientReportsViews.get(user.getUserRole()));
        modelAndView.addObject("reports", reportService.getClientReportsByFilter(dto));
        modelAndView.addObject("countOfPages", reportService.getCountOfPagesForClientReportsByFilter(dto));
        modelAndView.addObject("clientId", dto.getClientId());
        modelAndView.addObject("clientFullName", dto.getClientFullName());
        modelAndView.addObject("page", dto.getPage());
        modelAndView.addObject("dto", dto);
        return modelAndView;
    }

    @GetMapping("/data")
    public ModelAndView getReportData(@RequestParam("id") Long reportId) {
        ModelAndView modelAndView = new ModelAndView("/user/report.jsp");
        modelAndView.addObject("reportData", reportService.getReportData(reportId));
        return modelAndView;
    }

    @GetMapping("/edit")
    public ModelAndView getReportDataForEdit(@RequestParam("id") Long reportId, @ModelAttribute("message") String message) {
        ModelAndView modelAndView = new ModelAndView("/client/edit.jsp");
        modelAndView.addObject("reportData", reportService.getReportData(reportId));
        modelAndView.addObject("message", message);
        return modelAndView;
    }

    @PostMapping("/edit")
    public RedirectView editReportData(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        ReportData editedReportData = queryParameterResolver.getObject(request, ReportData.class);
        reportService.editReportData(editedReportData);
        redirectAttributes.addAttribute("id", editedReportData.getId());
        redirectAttributes.addFlashAttribute("message", "Report has been edited successfully!");
        return new RedirectView("/tax-office/service/report/edit");
    }

    @PostMapping("/update")
    public RedirectView updateReportStatusFromReportsPage(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        ReportUpdateDto dto = queryParameterResolver.getObject(request, ReportUpdateDto.class);
        reportService.updateReportStatus(dto);
        redirectAttributes.addAttribute("status", dto.getStatus());
        redirectAttributes.addAttribute("type", dto.getType());
        redirectAttributes.addAttribute("date", dto.getDate());
        redirectAttributes.addAttribute("page", dto.getPage());
        redirectAttributes.addAttribute("name", dto.getName());
        redirectAttributes.addAttribute("surname", dto.getSurname());
        redirectAttributes.addAttribute("tin", dto.getTin());
        return new RedirectView("/tax-office/service/report/filter");
    }

    @PostMapping("/update/client")
    public RedirectView updateReportStatusFromClientReportsPage(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        ReportUpdateDto dto = queryParameterResolver.getObject(request, ReportUpdateDto.class);
        reportService.updateReportStatus(dto);
        redirectAttributes.addAttribute("status", dto.getStatus());
        redirectAttributes.addAttribute("type", dto.getType());
        redirectAttributes.addAttribute("date", dto.getDate());
        redirectAttributes.addAttribute("page", dto.getPage());
        redirectAttributes.addAttribute("clientId", dto.getClientId());
        redirectAttributes.addAttribute("clientFullName", dto.getClientFullName());
        return new RedirectView("/tax-office/service/report/client/filter");
    }

    @PostMapping("/delete")
    public RedirectView deleteReport(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        ReportDeleteDto dto = queryParameterResolver.getObject(request, ReportDeleteDto.class);
        reportService.deleteReportById(dto.getId());
        redirectAttributes.addAttribute("status", dto.getStatus());
        redirectAttributes.addAttribute("type", dto.getType());
        redirectAttributes.addAttribute("date", dto.getDate());
        redirectAttributes.addAttribute("page", dto.getPage());
        redirectAttributes.addAttribute("clientId", dto.getClientId());
        return new RedirectView("/tax-office/service/report/client/filter");
    }
}
