package epam.project.app.infra.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class ProcessModelAndView {
    public void processModel(HttpServletRequest req, HttpServletResponse resp, ModelAndView modelAndView, HttpServlet servlet) throws IOException, ServletException {
        if (modelAndView.isRedirect()) {
            String request = buildRequest(modelAndView);
            resp.sendRedirect(req.getContextPath() + request);
            return;
        }
        RequestDispatcher dispatcher = servlet.getServletContext().getRequestDispatcher(modelAndView.getView());
        fillAttributes(req, modelAndView);
        dispatcher.forward(req, resp);
    }

    private void fillAttributes(HttpServletRequest request, ModelAndView modelAndView) {
        modelAndView.getAttributes().forEach(request::setAttribute);
    }

    private String buildRequest(ModelAndView modelAndView) {
        String view = modelAndView.getView();
        Map<String, Object> attributes = modelAndView.getAttributes();
        if (!attributes.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder(view);
            stringBuilder.append("?");
            Iterator<Map.Entry<String, Object>> iterator = attributes.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                stringBuilder.append(entry.getKey()).append("=").append(entry.getValue());
                if (iterator.hasNext()) {
                    stringBuilder.append("&");
                }
            }
            return stringBuilder.toString();
        }
        return view;
    }
}
