package org.project.spring.tax_office.logic.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.project.spring.tax_office.infra.web.QueryParameterResolver;
import org.project.spring.tax_office.logic.entity.report.ReportData;
import org.project.spring.tax_office.logic.service.ReportDataService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReportDataControllerTest {
    @Mock
    private ReportDataService reportDataService;
    @Mock
    private QueryParameterResolver queryParameterResolver;
    @Mock
    private HttpServletRequest request;
    @InjectMocks
    private ReportDataController reportDataController;

    @Test
    public void getReportData() {
        ReportData reportData = mock(ReportData.class);
        when(reportDataService.getReportData(anyLong())).thenReturn(reportData);

        ModelAndView modelAndView = reportDataController.getReportData(anyLong());
        assertNotNull(modelAndView);
        assertEquals("/user/report.jsp", modelAndView.getViewName());
        assertEquals(reportData, modelAndView.getModel().get("reportData"));

        verify(reportDataService).getReportData(anyLong());
    }

    @Test
    public void getReportDataForEdit() {
        String message = "some message";
        ReportData reportData = mock(ReportData.class);
        when(reportDataService.getReportData(anyLong())).thenReturn(reportData);

        ModelAndView modelAndView = reportDataController.getReportDataForEdit(anyLong(), message);
        assertNotNull(modelAndView);
        assertEquals("/client/edit.jsp", modelAndView.getViewName());
        assertEquals(reportData, modelAndView.getModel().get("reportData"));
        assertEquals(message, modelAndView.getModel().get("message"));

        verify(reportDataService).getReportData(anyLong());
    }

    @Test
    public void editReportData() {
        ReportData reportData = mock(ReportData.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        when(queryParameterResolver.getObject(request, ReportData.class)).thenReturn(reportData);
        when(reportDataService.editReportData(reportData)).thenReturn(1);

        RedirectView redirectView = reportDataController.editReportData(request, redirectAttributes);
        assertNotNull(redirectView);
        assertEquals("/tax-office/service/report/data/edit",redirectView.getUrl());

        verify(reportDataService).editReportData(reportData);
        verify(redirectAttributes).addFlashAttribute("message", "Report has been edited successfully!");
        verify(redirectAttributes).addAttribute("id", reportData.getId());
    }
}
