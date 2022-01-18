package epam.project.app.infra.web;

import epam.project.app.infra.web.exception.ExceptionHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class BaseServlet extends HttpServlet {
    @Getter
    private final String servletName;
    private final DispatcherRequest dispatcherRequest;
    private final ProcessModelAndView processModelAndView;
    private final ExceptionHandler exceptionHandler;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ModelAndView modelAndView;
        try {
            modelAndView = dispatcherRequest.processingRequest(req);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            modelAndView = exceptionHandler.processingException(ex);
        }
        processModelAndView.processModel(req, resp, modelAndView, this);
    }
}
