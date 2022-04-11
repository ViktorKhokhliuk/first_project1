package epam.project.app.logic.controller;

import epam.project.app.infra.web.ModelAndView;
import epam.project.app.infra.web.QueryParameterResolver;
import epam.project.app.logic.entity.dto.UserDTO;
import epam.project.app.logic.entity.user.Client;
import epam.project.app.logic.entity.user.Inspector;
import epam.project.app.logic.entity.user.User;
import epam.project.app.logic.entity.user.UserRole;
import epam.project.app.logic.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Locale;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

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

    private static final String LOGIN = "tony";
    private static final String PASSWORD = "tony123";
    private static final String SELECTED_LOCALE = "en";
    private static final String VIEW = "/index.jsp";
    private static final Long ID = 1L;

    @Before
    public void init() {
        when(request.getSession(true)).thenReturn(session);
        when(request.getSession(false)).thenReturn(session);
    }

    @Test
    public void loginWhenServiceReturnClient() {
        User user = getClient();
        UserDTO userDTO = new UserDTO(LOGIN,PASSWORD);

        when(userService.getUserByLogin(userDTO)).thenReturn(user);
        when(parameterResolver.getObject(request, UserDTO.class)).thenReturn(userDTO);

        ModelAndView modelAndView = userController.login(request);
        assertNotNull(modelAndView);
        assertEquals("/client/homePage.jsp", modelAndView.getView());
        assertTrue(modelAndView.isRedirect());

        verify(request).getSession(true);
        verify(session).setAttribute("user", user);
    }

    @Test
    public void loginWhenServiceReturnInspector() {
        User user = getInspector();
        UserDTO userDTO = new UserDTO(LOGIN,PASSWORD);

        when(userService.getUserByLogin(userDTO)).thenReturn(user);
        when(parameterResolver.getObject(request, UserDTO.class)).thenReturn(userDTO);

        ModelAndView modelAndView = userController.login(request);
        assertNotNull(modelAndView);
        assertEquals("/inspector/homePage.jsp", modelAndView.getView());
        assertTrue(modelAndView.isRedirect());

        verify(request).getSession(true);
        verify(session).setAttribute("user", user);
    }

    @Test
    public void changeLocale() {
        when(request.getParameter("selectedLocale")).thenReturn(SELECTED_LOCALE);
        when(request.getParameter("view")).thenReturn(VIEW);

        ModelAndView modelAndView = userController.changeLocale(request);
        assertNotNull(modelAndView);
        assertEquals(VIEW, modelAndView.getView());
        assertTrue(modelAndView.isRedirect());

        verify(session).setAttribute("selectedLocale", new Locale(SELECTED_LOCALE));
        verify(request).getSession(false);
    }

    @Test
    public void logout() {
        ModelAndView modelAndView = userController.logout(request);
        assertNotNull(modelAndView);
        assertEquals("/index.jsp", modelAndView.getView());
        assertTrue(modelAndView.isRedirect());
    }

    @Test
    public void toHomeForClient() {
        User user = getClient();

        when(session.getAttribute("user")).thenReturn(user);

        ModelAndView modelAndView = userController.toHome(request);
        assertNotNull(modelAndView);
        assertEquals("/client/homePage.jsp",modelAndView.getView());
        assertTrue(modelAndView.isRedirect());

        verify(session).getAttribute("user");
        verify(request).getSession(false);

    }

    @Test
    public void toHomeForInspector() {
        User user = getInspector();

        when(session.getAttribute("user")).thenReturn(user);

        ModelAndView modelAndView = userController.toHome(request);
        assertNotNull(modelAndView);
        assertEquals("/inspector/homePage.jsp",modelAndView.getView());
        assertTrue(modelAndView.isRedirect());

        verify(session).getAttribute("user");
        verify(request).getSession(false);
    }

    private User getInspector() {
        User user = new Inspector();
        user.setId(ID);
        user.setLogin(LOGIN);
        user.setPassword(PASSWORD);
        user.setUserRole(UserRole.INSPECTOR);
        return user;
    }

    private User getClient() {
        User user = new Client();
        user.setId(ID);
        user.setLogin(LOGIN);
        user.setPassword(PASSWORD);
        user.setUserRole(UserRole.CLIENT);
        return user;
    }
}
