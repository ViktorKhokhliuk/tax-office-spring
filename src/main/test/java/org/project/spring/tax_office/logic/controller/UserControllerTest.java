package org.project.spring.tax_office.logic.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.project.spring.tax_office.infra.web.QueryParameterResolver;
import org.project.spring.tax_office.logic.entity.dto.UserAuthorizationDto;
import org.project.spring.tax_office.logic.entity.user.Client;
import org.project.spring.tax_office.logic.entity.user.Inspector;
import org.project.spring.tax_office.logic.entity.user.User;
import org.project.spring.tax_office.logic.entity.user.UserRole;
import org.project.spring.tax_office.logic.service.UserService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Locale;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    private Map<UserRole, String> userHomePageViews;
    @Mock
    private UserService userService;
    @Mock
    private QueryParameterResolver parameterResolver;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;

    @InjectMocks
    private UserController userController;

    private static final String SELECTED_LOCALE = "en";
    private static final String VIEW = "/index.jsp";

    @Before
    public void init() {
        when(request.getSession(true)).thenReturn(session);
        when(request.getSession(false)).thenReturn(session);
    }

    @Test
    public void loginWhenServiceReturnClient() {
        User user = mock(Client.class);
        UserAuthorizationDto dto = mock(UserAuthorizationDto.class);

        when(userService.getUserByLogin(dto)).thenReturn(user);
        when(parameterResolver.getObject(request, UserAuthorizationDto.class)).thenReturn(dto);
        when(userHomePageViews.get(user.getUserRole())).thenReturn("/client/homePage.jsp");

        RedirectView redirectView = userController.login(request);

        assertNotNull(redirectView);
        assertEquals("/tax-office/client/homePage.jsp", redirectView.getUrl());

        verify(request).getSession(true);
        verify(session).setAttribute("user", user);
    }

    @Test
    public void loginWhenServiceReturnInspector() {
        User user = mock(Inspector.class);
        UserAuthorizationDto dto = mock(UserAuthorizationDto.class);

        when(userService.getUserByLogin(dto)).thenReturn(user);
        when(parameterResolver.getObject(request, UserAuthorizationDto.class)).thenReturn(dto);
        when(userHomePageViews.get(user.getUserRole())).thenReturn("/inspector/homePage.jsp");

        RedirectView redirectView = userController.login(request);

        assertNotNull(redirectView);
        assertEquals("/tax-office/inspector/homePage.jsp", redirectView.getUrl());

        verify(request).getSession(true);
        verify(session).setAttribute("user", user);
    }

    @Test
    public void changeLocale() {
        when(request.getParameter("selectedLocale")).thenReturn(SELECTED_LOCALE);
        when(request.getParameter("view")).thenReturn(VIEW);

        RedirectView redirectView = userController.changeLocale(request);
        assertNotNull(redirectView);
        assertEquals("/tax-office" + VIEW, redirectView.getUrl());

        verify(session).setAttribute("selectedLocale", new Locale(SELECTED_LOCALE));
        verify(request).getSession(false);
    }

    @Test
    public void logout() {
        RedirectView redirectView = userController.logout(request);
        assertNotNull(redirectView);
        assertEquals("/tax-office/index.jsp", redirectView.getUrl());

        verify(request).getSession(false);
        verify(session).invalidate();
    }

    @Test
    public void toHomeForClient() {
        User user = mock(Client.class);

        when(session.getAttribute("user")).thenReturn(user);
        when(userHomePageViews.get(user.getUserRole())).thenReturn("/client/homePage.jsp");

        ModelAndView modelAndView = userController.toHome(request);
        assertNotNull(modelAndView);
        assertEquals("/client/homePage.jsp", modelAndView.getViewName());

        verify(session).getAttribute("user");
        verify(request).getSession(false);

    }

    @Test
    public void toHomeForInspector() {
        User user = mock(Client.class);

        when(session.getAttribute("user")).thenReturn(user);
        when(userHomePageViews.get(user.getUserRole())).thenReturn("/inspector/homePage.jsp");

        ModelAndView modelAndView = userController.toHome(request);
        assertNotNull(modelAndView);
        assertEquals("/inspector/homePage.jsp", modelAndView.getViewName());

        verify(session).getAttribute("user");
        verify(request).getSession(false);
    }
}
