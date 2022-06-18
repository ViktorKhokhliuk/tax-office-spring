package org.project.spring.tax_office.logic.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.project.spring.tax_office.infra.web.QueryParameterResolver;
import org.project.spring.tax_office.logic.entity.dto.ClientReportFilterDto;
import org.project.spring.tax_office.logic.entity.dto.ReportDeleteDto;
import org.project.spring.tax_office.logic.entity.dto.ReportFilterDto;
import org.project.spring.tax_office.logic.entity.dto.ReportUpdateDto;
import org.project.spring.tax_office.logic.entity.report.Report;
import org.project.spring.tax_office.logic.entity.user.Client;
import org.project.spring.tax_office.logic.entity.user.Inspector;
import org.project.spring.tax_office.logic.entity.user.User;
import org.project.spring.tax_office.logic.entity.user.UserRole;
import org.project.spring.tax_office.logic.service.ClientService;
import org.project.spring.tax_office.logic.service.ReportService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReportControllerTest {
    @Mock
    private ReportService reportService;
    @Mock
    private ClientService clientService;
    @Mock
    private Map<UserRole, String> clientReportsViews;
    @Mock
    private QueryParameterResolver queryParameterResolver;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @InjectMocks
    private ReportController reportController;

    @Test
    public void getAll() {
        List<Report> reports = List.of(mock(Report.class), mock(Report.class));
        Map<Long, Client> clients = Map.of(1L, mock(Client.class), 2L, mock(Client.class));
        double countOfPages = 3.0;
        int page = 1;

        when(reportService.getAllReports(page)).thenReturn(reports);
        when(clientService.getAllClientsNoLimitMap()).thenReturn(clients);
        when(reportService.getCountOfPagesForAll()).thenReturn(countOfPages);

        ModelAndView modelAndView = reportController.getAll(page);
        assertNotNull(modelAndView);
        assertEquals("/inspector/reports.jsp", modelAndView.getViewName());
        assertEquals(page, modelAndView.getModel().get("page"));
        assertEquals(clients, modelAndView.getModel().get("clients"));
        assertEquals(reports, modelAndView.getModel().get("reports"));
        assertEquals(countOfPages, modelAndView.getModel().get("countOfPages"));

        verify(reportService).getAllReports(page);
        verify(clientService).getAllClientsNoLimitMap();
        verify(reportService).getCountOfPagesForAll();
    }

    @Test
    public void getAllByFilter() {
        ReportFilterDto dto = mock(ReportFilterDto.class);
        List<Report> reports = List.of(mock(Report.class), mock(Report.class));
        Map<Long, Client> clients = Map.of(1L, mock(Client.class), 2L, mock(Client.class));
        double countOfPages = 3.0;
        int page = 1;

        when(queryParameterResolver.getObject(request, ReportFilterDto.class)).thenReturn(dto);
        when(reportService.getAllReportsByFilter(dto)).thenReturn(reports);
        when(clientService.getAllClientsNoLimitMap()).thenReturn(clients);
        when(reportService.getCountOfPagesForAllReportsByFilter(dto)).thenReturn(countOfPages);
        when(dto.getPage()).thenReturn(page);

        ModelAndView modelAndView = reportController.getAllByFilter(request);
        assertNotNull(modelAndView);
        assertEquals("/inspector/reports.jsp", modelAndView.getViewName());
        assertEquals(page, modelAndView.getModel().get("page"));
        assertEquals(clients, modelAndView.getModel().get("clients"));
        assertEquals(reports, modelAndView.getModel().get("reports"));
        assertEquals(countOfPages, modelAndView.getModel().get("countOfPages"));
        assertEquals(dto, modelAndView.getModel().get("dto"));

        verify(reportService).getAllReportsByFilter(dto);
        verify(clientService).getAllClientsNoLimitMap();
        verify(reportService).getCountOfPagesForAllReportsByFilter(dto);
    }

    @Test
    public void getClientReports() {
        int page = 1;
        long clientId = 1L;
        double countOfPages = 3.0;
        String clientFullName = "tony soprano";
        User user = mock(Inspector.class);
        List<Report> reports = List.of(mock(Report.class), mock(Report.class));

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("clientFullName")).thenReturn(clientFullName);
        when(request.getParameter("page")).thenReturn(String.valueOf(page));
        when(request.getParameter("clientId")).thenReturn(String.valueOf(clientId));
        when(clientReportsViews.get(user.getUserRole())).thenReturn("/inspector/clientReports.jsp");
        when(reportService.getClientReports(clientId, page)).thenReturn(reports);
        when(reportService.getCountOfPagesForClientReports(clientId)).thenReturn(countOfPages);

        ModelAndView modelAndView = reportController.getClientReports(request);
        assertNotNull(modelAndView);
        assertEquals("/inspector/clientReports.jsp", modelAndView.getViewName());
        assertEquals(page, modelAndView.getModel().get("page"));
        assertEquals(reports, modelAndView.getModel().get("reports"));
        assertEquals(countOfPages, modelAndView.getModel().get("countOfPages"));
        assertEquals(clientId, modelAndView.getModel().get("clientId"));
        assertEquals(clientFullName, modelAndView.getModel().get("clientFullName"));

        verify(request).getSession(false);
        verify(session).getAttribute("user");
        verify(clientReportsViews).get(user.getUserRole());
        verify(reportService).getClientReports(clientId, page);
        verify(reportService).getCountOfPagesForClientReports(clientId);
    }

    @Test
    public void getClientReportsByFilter() {
        int page = 1;
        long clientId = 1L;
        double countOfPages = 3.0;
        String clientFullName = "tony soprano";
        User user = mock(Inspector.class);
        ClientReportFilterDto dto = mock(ClientReportFilterDto.class);
        List<Report> reports = List.of(mock(Report.class), mock(Report.class));

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(queryParameterResolver.getObject(request, ClientReportFilterDto.class)).thenReturn(dto);
        when(clientReportsViews.get(user.getUserRole())).thenReturn("/inspector/clientReports.jsp");
        when(reportService.getClientReportsByFilter(dto)).thenReturn(reports);
        when(reportService.getCountOfPagesForClientReportsByFilter(dto)).thenReturn(countOfPages);
        when(dto.getPage()).thenReturn(page);
        when(dto.getClientFullName()).thenReturn(clientFullName);
        when(dto.getClientId()).thenReturn(clientId);

        ModelAndView modelAndView = reportController.getClientReportsByFilter(request);
        assertNotNull(modelAndView);
        assertEquals("/inspector/clientReports.jsp", modelAndView.getViewName());
        assertEquals(page, modelAndView.getModel().get("page"));
        assertEquals(reports, modelAndView.getModel().get("reports"));
        assertEquals(countOfPages, modelAndView.getModel().get("countOfPages"));
        assertEquals(clientId, modelAndView.getModel().get("clientId"));
        assertEquals(clientFullName, modelAndView.getModel().get("clientFullName"));
        assertEquals(dto, modelAndView.getModel().get("dto"));

        verify(request).getSession(false);
        verify(session).getAttribute("user");
        verify(clientReportsViews).get(user.getUserRole());
        verify(reportService).getClientReportsByFilter(dto);
        verify(reportService).getCountOfPagesForClientReportsByFilter(dto);
    }

    @Test
    public void updateReportStatusFromReportsPage() {
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        ReportUpdateDto dto = mock(ReportUpdateDto.class);

        when(queryParameterResolver.getObject(request, ReportUpdateDto.class)).thenReturn(dto);
        when(reportService.updateReportStatus(dto)).thenReturn(dto);

        RedirectView redirectView = reportController.updateReportStatusFromReportsPage(request, redirectAttributes);
        assertNotNull(redirectView);
        assertEquals("/tax-office/service/report/filter", redirectView.getUrl());

        verify(reportService).updateReportStatus(dto);
        verify(redirectAttributes).addAttribute("status", dto.getStatus());
        verify(redirectAttributes).addAttribute("type", dto.getType());
        verify(redirectAttributes).addAttribute("date", dto.getDate());
        verify(redirectAttributes).addAttribute("page", dto.getPage());
        verify(redirectAttributes).addAttribute("name", dto.getName());
        verify(redirectAttributes).addAttribute("surname", dto.getSurname());
        verify(redirectAttributes).addAttribute("tin", dto.getTin());
    }

    @Test
    public void updateReportStatusFromClientReportsPage() {
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        ReportUpdateDto dto = mock(ReportUpdateDto.class);

        when(queryParameterResolver.getObject(request, ReportUpdateDto.class)).thenReturn(dto);
        when(reportService.updateReportStatus(dto)).thenReturn(dto);

        RedirectView redirectView = reportController.updateReportStatusFromClientReportsPage(request, redirectAttributes);
        assertNotNull(redirectView);
        assertEquals("/tax-office/service/report/client/filter", redirectView.getUrl());

        verify(reportService).updateReportStatus(dto);
        verify(redirectAttributes).addAttribute("status", dto.getStatus());
        verify(redirectAttributes).addAttribute("type", dto.getType());
        verify(redirectAttributes).addAttribute("date", dto.getDate());
        verify(redirectAttributes).addAttribute("page", dto.getPage());
        verify(redirectAttributes).addAttribute("clientId", dto.getClientId());
        verify(redirectAttributes).addAttribute("clientFullName", dto.getClientFullName());
    }

    @Test
    public void deleteReport() {
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        ReportDeleteDto dto = mock(ReportDeleteDto.class);

        when(queryParameterResolver.getObject(request, ReportDeleteDto.class)).thenReturn(dto);
        when(reportService.deleteReportById(dto.getId())).thenReturn(1);

        RedirectView redirectView = reportController.deleteReport(request, redirectAttributes);
        assertNotNull(redirectView);
        assertEquals("/tax-office/service/report/client/filter", redirectView.getUrl());

        verify(reportService).deleteReportById(dto.getId());
        verify(redirectAttributes).addAttribute("status", dto.getStatus());
        verify(redirectAttributes).addAttribute("type", dto.getType());
        verify(redirectAttributes).addAttribute("date", dto.getDate());
        verify(redirectAttributes).addAttribute("page", dto.getPage());
        verify(redirectAttributes).addAttribute("clientId", dto.getClientId());
    }
}
