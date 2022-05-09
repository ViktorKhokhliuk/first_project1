package epam.project.app.logic.controller;

import epam.project.app.logic.entity.dto.UserDTO;
import epam.project.app.logic.entity.user.User;
import epam.project.app.logic.entity.user.UserRole;
import epam.project.app.logic.service.UserService;
import epam.project.app.infra.web.ModelAndView;
import epam.project.app.infra.web.QueryParameterResolver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Locale;
@Log4j2
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final QueryParameterResolver parameterResolver;

    public ModelAndView login(HttpServletRequest request) {
        UserDTO userDTO = parameterResolver.getObject(request, UserDTO.class);
        User userByLogin = userService.getUserByLogin(userDTO);

        ModelAndView modelAndView = new ModelAndView();
        if (userByLogin.getUserRole().toString().equals(UserRole.INSPECTOR.toString())) {
            modelAndView.setView("/inspector/homePage.jsp");
        } else {
            modelAndView.setView("/client/homePage.jsp");
        }
        modelAndView.setRedirect(true);
        HttpSession session = request.getSession(true);
        session.setAttribute("user", userByLogin);
        return modelAndView;
    }

    public ModelAndView logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView("/index.jsp");
        modelAndView.setRedirect(true);
        return modelAndView;
    }

    public ModelAndView changeLocale(HttpServletRequest request) {
        String selectedLocale = request.getParameter("selectedLocale");
        String view = request.getParameter("view");
        Locale locale = new Locale(selectedLocale);
        HttpSession session = request.getSession(false);
        session.setAttribute("selectedLocale", locale);
        log.info("Set session selected locale --> " + selectedLocale);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(view);
        modelAndView.setRedirect(true);
        return modelAndView;
    }

    public ModelAndView toHome(HttpServletRequest request) {
        User user = (User) request.getSession(false).getAttribute("user");
        ModelAndView modelAndView = new ModelAndView();
        if (user.getUserRole().equals(UserRole.CLIENT))
            modelAndView.setView("/client/homePage.jsp");
        else
            modelAndView.setView("/inspector/homePage.jsp");
        modelAndView.setRedirect(true);
        return modelAndView;
    }
}
