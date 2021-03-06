package org.project.spring.tax_office.logic.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.project.spring.tax_office.logic.entity.dto.ReportCreateDto;
import org.project.spring.tax_office.logic.service.ReportUploadService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;

@Log4j2
@Controller
@RequestMapping("/report/upload")
@RequiredArgsConstructor
public class ReportUploadController {
    private final ReportUploadService reportUploadService;

    private static final String UPLOAD_DIRECTORY = "webapp/upload/";

    @SneakyThrows
    @PostMapping()
    public RedirectView uploadReport(@RequestParam("file") MultipartFile multipartFile,
                                     @RequestParam("clientId") Long clientId,
                                     @RequestParam("type") String type,
                                     RedirectAttributes redirectAttributes) {

        String fileName = multipartFile.getOriginalFilename();
        String path = UPLOAD_DIRECTORY + clientId + fileName;

        File file = new File(path);
        multipartFile.transferTo(file);
        reportUploadService.uploadReport(new ReportCreateDto(clientId, fileName, type), file);
        log.log(Level.INFO, "File " + fileName + " has been uploaded successfully!");
        redirectAttributes.addFlashAttribute("message", "You have successfully uploaded " + fileName + " !");
        return new RedirectView("/tax-office/service/report/upload");
    }

    @GetMapping()
    public ModelAndView redirectToHome(@ModelAttribute("message") String message) {
        ModelAndView modelAndView = new ModelAndView("/client/homePage.jsp");
        modelAndView.addObject("message", message);
        return modelAndView;
    }
}
