package epam.project.app.infra.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ProcessModelAndView {
    public void processModel(HttpServletRequest req, HttpServletResponse resp, ModelAndView modelAndView, HttpServlet servlet) throws IOException, ServletException {
        if (modelAndView.isRedirect()) {
            resp.sendRedirect(req.getContextPath() + modelAndView.getView());
            return;
        }
        RequestDispatcher dispatcher = servlet.getServletContext().getRequestDispatcher(modelAndView.getView());
        fillAttributes(req, modelAndView);
        dispatcher.forward(req, resp);
    }

    private void fillAttributes(HttpServletRequest request, ModelAndView modelAndView) {
        modelAndView.getAttributes().forEach(request::setAttribute);
    }
}
