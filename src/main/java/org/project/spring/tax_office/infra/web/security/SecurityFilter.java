package org.project.spring.tax_office.infra.web.security;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.project.spring.tax_office.logic.entity.user.User;
import org.project.spring.tax_office.logic.entity.user.UserRole;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SecurityFilter implements Filter {
    private List<RequestMatcher> requestMatchers;

    @Override
    public void init(FilterConfig filterConfig) {
        requestMatchers = new ArrayList<>();

        requestMatchers.add(new RequestMatcher("/inspector/homePage.jsp", UserRole.INSPECTOR));
        requestMatchers.add(new RequestMatcher("/inspector/clients.jsp", UserRole.INSPECTOR));
        requestMatchers.add(new RequestMatcher("/inspector/reports.jsp", UserRole.INSPECTOR));
        requestMatchers.add(new RequestMatcher("/inspector/clientReports.jsp", UserRole.INSPECTOR));
        requestMatchers.add(new RequestMatcher("/service/searchClients", UserRole.INSPECTOR));
        requestMatchers.add(new RequestMatcher("/service/allClients", UserRole.INSPECTOR));
        requestMatchers.add(new RequestMatcher("/service/allReports", UserRole.INSPECTOR));
        requestMatchers.add(new RequestMatcher("/service/filterAllReports", UserRole.INSPECTOR));

        requestMatchers.add(new RequestMatcher("/client/edit.jsp", UserRole.CLIENT));
        requestMatchers.add(new RequestMatcher("/client/reports.jsp", UserRole.CLIENT));
        requestMatchers.add(new RequestMatcher("/client/homePage.jsp", UserRole.CLIENT));
        requestMatchers.add(new RequestMatcher("/service/upload", UserRole.CLIENT));
        requestMatchers.add(new RequestMatcher("/service/editReport", UserRole.CLIENT));

        requestMatchers.add(new RequestMatcher("/user/report.jsp", UserRole.CLIENT, UserRole.INSPECTOR));
        requestMatchers.add(new RequestMatcher("/service/toHome", UserRole.CLIENT, UserRole.INSPECTOR));
        requestMatchers.add(new RequestMatcher("/service/allReportsByClient", UserRole.CLIENT, UserRole.INSPECTOR));
        requestMatchers.add(new RequestMatcher("/service/filterClientReports", UserRole.CLIENT, UserRole.INSPECTOR));
        requestMatchers.add(new RequestMatcher("/service/showReport", UserRole.CLIENT, UserRole.INSPECTOR));

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String pathWithoutContext = getPathWithoutContext(request);

        Boolean hasAccess = requestMatchers.stream()
                .filter(requestMatcher -> requestMatcher.pathMatch(pathWithoutContext))
                .findFirst()
                .map(requestMatcher -> hasRole(requestMatcher, request))
                .orElse(true);

        if (hasAccess) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error/forbidden.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    public void destroy() {
        requestMatchers.clear();
    }

    private String getPathWithoutContext(HttpServletRequest httpServletRequest) {
        int contextPathLength = httpServletRequest.getContextPath().length();
        return httpServletRequest.getRequestURI().substring(contextPathLength);
    }

    private boolean hasRole(RequestMatcher requestMatcher, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && requestMatcher.hasRole((User) session.getAttribute("user"));

    }
}
