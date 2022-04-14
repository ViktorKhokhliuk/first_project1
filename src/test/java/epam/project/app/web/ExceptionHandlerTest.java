package epam.project.app.web;

import epam.project.app.infra.web.ModelAndView;
import epam.project.app.infra.web.exception.AppException;
import epam.project.app.infra.web.exception.ExceptionHandler;
import epam.project.app.logic.exception.ReportException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(MockitoJUnitRunner.class)
public class ExceptionHandlerTest {

    @InjectMocks
    ExceptionHandler exceptionHandler;

    @Test
    public void processingException() {
        ModelAndView modelAndView = exceptionHandler.processingException(new RuntimeException());
        assertEquals("/error/internalError.jsp", modelAndView.getView());
        assertFalse(modelAndView.isRedirect());

        ModelAndView modelAndView1 = exceptionHandler.processingException(new AppException("some message"));
        assertEquals("/error/exception.jsp", modelAndView1.getView());
        assertFalse(modelAndView.isRedirect());

        ModelAndView modelAndView2 = exceptionHandler.processingException(new ReportException("some message"));
        assertEquals("/error/exception.jsp", modelAndView2.getView());
        assertFalse(modelAndView.isRedirect());
    }
}
