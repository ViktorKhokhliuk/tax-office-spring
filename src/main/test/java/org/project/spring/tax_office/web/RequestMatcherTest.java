package org.project.spring.tax_office.web;

import org.junit.Before;
import org.junit.Test;
import org.project.spring.tax_office.infra.web.security.RequestMatcher;
import org.project.spring.tax_office.logic.entity.user.Client;
import org.project.spring.tax_office.logic.entity.user.Inspector;
import org.project.spring.tax_office.logic.entity.user.User;
import org.project.spring.tax_office.logic.entity.user.UserRole;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RequestMatcherTest {

    private RequestMatcher requestMatcher;

    @Before
    public void init() {
        String pathRegex = "/inspector/homePage.jsp";
        requestMatcher = new RequestMatcher(pathRegex, UserRole.INSPECTOR);
    }

    @Test
    public void pathMatch() {
        String expectedTrue = "/inspector/homePage.jsp";
        String expectedFalse = "/client/homePage.jsp";

        boolean resultWithExpectedTrue = requestMatcher.pathMatch(expectedTrue);
        boolean resultWithExpectedFalse = requestMatcher.pathMatch(expectedFalse);

        assertTrue(resultWithExpectedTrue);
        assertFalse(resultWithExpectedFalse);
    }

    @Test
    public void hasRole() {
        User client = new Client();
        User inspector = new Inspector();
        inspector.setUserRole(UserRole.INSPECTOR);

        boolean resultWithClient = requestMatcher.hasRole(client);
        boolean resultWithInspector = requestMatcher.hasRole(inspector);

        assertFalse(resultWithClient);
        assertTrue(resultWithInspector);
    }
}
