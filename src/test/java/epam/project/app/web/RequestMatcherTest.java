package epam.project.app.web;

import epam.project.app.infra.web.RequestMatcher;
import epam.project.app.logic.entity.user.Client;
import epam.project.app.logic.entity.user.Inspector;
import epam.project.app.logic.entity.user.User;
import epam.project.app.logic.entity.user.UserRole;
import org.junit.Before;
import org.junit.Test;

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
