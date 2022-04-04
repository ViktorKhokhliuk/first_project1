package epam.project.app.web;

import epam.project.app.infra.web.SecurityFilter;
import epam.project.app.logic.entity.user.Client;
import epam.project.app.logic.entity.user.Inspector;
import epam.project.app.logic.entity.user.User;
import epam.project.app.logic.entity.user.UserRole;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SecurityFilterTest {

    @Mock
    private FilterChain filterChain;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterConfig filterConfig;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher requestDispatcher;

    @InjectMocks
    private SecurityFilter securityFilter;

    @Before
    public void init() {
        securityFilter.init(filterConfig);
        when(request.getContextPath()).thenReturn("/tax-office");
        when(request.getRequestURI()).thenReturn("/tax-office/service/allClients");
        when(request.getSession(false)).thenReturn(session);
    }

    @Test
    public void doFilterWhenHasAccess() throws ServletException, IOException {
        User inspector = new Inspector();
        inspector.setUserRole(UserRole.INSPECTOR);
        when(session.getAttribute("user")).thenReturn(inspector);
        securityFilter.doFilter(request, response, filterChain);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void doFilterWhenHasNoAccess() throws ServletException, IOException {
        User client = new Client();
        when(session.getAttribute("user")).thenReturn(client);
        when(request.getRequestDispatcher("/error/forbidden.jsp")).thenReturn(requestDispatcher);
        securityFilter.doFilter(request,response,filterChain);
        verify(requestDispatcher).forward(request,response);
    }

    @Test
    public void doFilterWhenUserIsNull() throws ServletException, IOException {
        when(session.getAttribute("user")).thenReturn(null);
        when(request.getRequestDispatcher("/error/forbidden.jsp")).thenReturn(requestDispatcher);
        securityFilter.doFilter(request,response,filterChain);
        verify(requestDispatcher).forward(request,response);
    }

    @Test
    public void doFilterWhenPathNoMath()throws ServletException, IOException {
        User client = new Client();
        lenient().when(session.getAttribute("user")).thenReturn(client);
        when(request.getRequestURI()).thenReturn("/tax-office/service/allClients.jsp");
        securityFilter.doFilter(request,response,filterChain);
        verify(filterChain).doFilter(request,response);
    }
}
