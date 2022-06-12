package org.project.spring.tax_office.logic.controller.report;

import lombok.RequiredArgsConstructor;
import org.project.spring.tax_office.infra.web.QueryParameterResolver;
import org.project.spring.tax_office.logic.entity.user.User;
import org.project.spring.tax_office.logic.entity.user.UserRole;
import org.project.spring.tax_office.logic.service.ReportService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final Map<UserRole, String> reportViews;
    private final QueryParameterResolver queryParameterResolver;

    @GetMapping()
    public ModelAndView getAll(@RequestParam("page") int page) {
        ModelAndView modelAndView = new ModelAndView("/inspector/reports.jsp");
        modelAndView.addObject("reports", reportService.getAll(page));
        modelAndView.addObject("countOfPages", reportService.getCountOfPagesForAll());
        modelAndView.addObject("page", page);
        return modelAndView;
    }

    @GetMapping("/client")
    public ModelAndView getClientReports(HttpServletRequest request) {
        User user = (User) request.getSession(false).getAttribute("user");
        int page = Integer.parseInt(request.getParameter("page"));
        Long clientId = Long.valueOf(request.getParameter("clientId"));
        ModelAndView modelAndView = new ModelAndView(reportViews.get(user.getUserRole()));
        modelAndView.addObject("reports", reportService.getClientReports(clientId, page));
        modelAndView.addObject("countOfPages", reportService.getCountOfPagesForClientReports(clientId));
        modelAndView.addObject("page", page);
        return modelAndView;
    }
}
