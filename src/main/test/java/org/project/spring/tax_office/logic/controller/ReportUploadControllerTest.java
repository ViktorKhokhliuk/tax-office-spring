package org.project.spring.tax_office.logic.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.project.spring.tax_office.logic.entity.dto.ReportCreateDto;
import org.project.spring.tax_office.logic.entity.report.Report;
import org.project.spring.tax_office.logic.service.ReportUploadService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReportUploadControllerTest {
    @Mock
    private ReportUploadService reportUploadService;
    @InjectMocks
    private ReportUploadController reportUploadController;

    @Test
    public void uploadReport() throws IOException {
        long clientId = 0L;
        String type = "tax";
        String fileName = "file";
        Report report = mock(Report.class);
        MultipartFile multipartFile = mock(MultipartFile.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        when(reportUploadService.uploadReport(any(ReportCreateDto.class), any(File.class))).thenReturn(report);
        when(multipartFile.getOriginalFilename()).thenReturn(fileName);

        RedirectView redirectView = reportUploadController.uploadReport(multipartFile, clientId, type, redirectAttributes);
        assertNotNull(redirectView);
        assertEquals("/tax-office/service/report/upload", redirectView.getUrl());

        verify(redirectAttributes).addFlashAttribute("message", "You have successfully uploaded " + fileName + " !");
        verify(multipartFile).getOriginalFilename();
        verify(multipartFile).transferTo(any(File.class));
    }

    @Test
    public void redirectToHome() {
        String message = "some message";
        ModelAndView modelAndView = reportUploadController.redirectToHome(message);

        assertNotNull(modelAndView);
        assertEquals("/client/homePage.jsp", modelAndView.getViewName());
        assertEquals(message, modelAndView.getModel().get("message"));
    }
}
