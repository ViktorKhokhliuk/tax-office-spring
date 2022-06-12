package org.project.spring.tax_office.logic.controller.report;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.project.spring.tax_office.logic.entity.dto.ReportCreateDto;
import org.project.spring.tax_office.logic.entity.user.User;
import org.project.spring.tax_office.logic.service.ReportService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.net.URI;
import java.nio.file.Path;

@Log4j2
@Controller
@RequestMapping("/report/upload")
@RequiredArgsConstructor
public class ReportUploadController {
    private final ReportService reportService;

    private static final String UPLOAD_DIRECTORY = "upload";

//    @SneakyThrows
//    @PostMapping()
//    public RedirectView uploadReport(HttpServletRequest request) {
//        User userFromSession = (User) request.getSession(false).getAttribute("user");
//        String uploadPath = request.getServletContext().getRealPath("") + UPLOAD_DIRECTORY;
//        Part part = request.getPart("file");
//        String type = request.getParameter("type");
//        String fileName = getFileName(part, userFromSession.getId());
//        String path = uploadPath + File.separator + fileName;
//        part.write(path);
//        reportService.uploadReport(new ReportCreateDto(userFromSession.getId(), fileName, type), path);
//        log.log(Level.INFO, "File " + fileName + " has uploaded successfully!");
//
//        return new RedirectView("/client/homePage.jsp");
//    }
    @SneakyThrows
    @PostMapping()
    public RedirectView uploadReport(@RequestParam("file") MultipartFile multipartFile,
                                     @RequestParam("clientId") Long clientId,
                                     @RequestParam("type") String type,
                                     RedirectAttributes redirectAttributes) {
        String fileName = multipartFile.getOriginalFilename();
        String path = "webapp/upload/" + clientId + fileName;
        File file = new File(path);
        multipartFile.transferTo(file);
        reportService.uploadReport(new ReportCreateDto(clientId, fileName, type), file);
        log.log(Level.INFO, "File " + fileName + " has uploaded successfully!");
        redirectAttributes.addAttribute("message", "You successfully uploaded " + fileName + "!");
        return new RedirectView("/tax-office/client/homePage.jsp");
    }
}
