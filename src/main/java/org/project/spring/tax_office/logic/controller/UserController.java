package org.project.spring.tax_office.logic.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.project.spring.tax_office.infra.web.QueryParameterResolver;
import org.project.spring.tax_office.logic.entity.dto.UserAuthorizationDto;
import org.project.spring.tax_office.logic.entity.user.User;
import org.project.spring.tax_office.logic.entity.user.UserRole;
import org.project.spring.tax_office.logic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Map;
@Log4j2
@Controller
@RequiredArgsConstructor
public class UserController {

    private final Map<UserRole, String> userHomePageViews;
    private final UserService userService;
    private final QueryParameterResolver queryParameterResolver;
    private static final String CONTEXT_PATH = "/tax-office";

    @PostMapping("/login")
    public RedirectView login(HttpServletRequest request) {
        UserAuthorizationDto userAuthorizationDto = queryParameterResolver.getObject(request, UserAuthorizationDto.class);
        User registeredUser = userService.getUserByLogin(userAuthorizationDto);
        HttpSession session = request.getSession(true);
        session.setAttribute("user", registeredUser);
        return new RedirectView(CONTEXT_PATH + userHomePageViews.get(registeredUser.getUserRole()));
    }

    @PostMapping("/logout")
    public RedirectView logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null)
            session.invalidate();

        return new RedirectView(CONTEXT_PATH + "/index.jsp");
    }

    @PostMapping("/changeLocale")
    public RedirectView changeLocale(HttpServletRequest request) {
        String selectedLocale = request.getParameter("selectedLocale");
        String view = request.getParameter("view");
        Locale locale = new Locale(selectedLocale);
        HttpSession session = request.getSession(false);
        session.setAttribute("selectedLocale", locale);
        log.info("Set session selected locale --> " + selectedLocale);
        return new RedirectView(CONTEXT_PATH + view);
    }

    @GetMapping("/home")
    public ModelAndView toHome(HttpServletRequest request) {
        User user = (User) request.getSession(false).getAttribute("user");
        return new ModelAndView(userHomePageViews.get(user.getUserRole()));
    }
}
