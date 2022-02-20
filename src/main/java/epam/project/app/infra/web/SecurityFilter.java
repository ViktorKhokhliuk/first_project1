package epam.project.app.infra.web;


import epam.project.app.logic.entity.user.User;
import epam.project.app.logic.entity.user.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SecurityFilter implements Filter {
    private List<RequestMatcher> requestMatchers;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        requestMatchers = new ArrayList<>();

        requestMatchers.add(new RequestMatcher("inspector/homePage.jsp", UserRole.INSPECTOR));
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
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error/forbiden.jsp");
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
