package epam.project.app.logic.controller;

import epam.project.app.infra.web.ModelAndView;
import epam.project.app.infra.web.exception.AppException;
import epam.project.app.logic.entity.dto.ReportCreateDto;
import epam.project.app.logic.entity.report.Report;
import epam.project.app.logic.entity.user.Client;
import epam.project.app.logic.entity.user.User;
import epam.project.app.logic.service.ReportService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReportUploadControllerTest {

    @Mock
    private ReportService reportService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private Part part;
    @Mock
    private ServletContext servletContext;
    @Mock
    private HttpSession session;

    @InjectMocks
    private ReportUploadController reportUploadController;

    private static final Long ID = 0L;
    private static final String LOGIN = "tony";
    private static final String TYPE = "single tax";
    private static final String FILE_NAME_XML = "XmlFile.xml";
    private static final String FILE_NAME_JSON = "JsonFile.json";
    private static final String WRONG_FILE_NAME = "File.txt";
    private static final String REAL_PATH = "D:\\Projects\\My\\tax-office\\webapp\\";
    private static final String PATH = "D:\\Projects\\My\\tax-office\\webapp\\upload\\id0";

    @Before
    public void init() throws ServletException, IOException {
        User user = new Client();
        user.setId(ID);
        user.setLogin(LOGIN);
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getServletContext()).thenReturn(servletContext);
        when(servletContext.getRealPath("")).thenReturn(REAL_PATH);
        when(request.getPart("part")).thenReturn(part);
        when(request.getParameter("type")).thenReturn(TYPE);
        when(reportService.uploadReport(any(ReportCreateDto.class))).thenReturn(new Report());
    }

    @Test
    public void uploadFileXml() throws IOException {
        String path = PATH + File.separator + FILE_NAME_XML;

        when(part.getHeader("content-disposition")).thenReturn("form-data; name=\"part\"; filename=\"" + FILE_NAME_XML + "\"");
        when(reportService.xmlValidation(path)).thenReturn(true);

        ModelAndView modelAndView = reportUploadController.uploadFile(request);
        assertNotNull(modelAndView);
        assertEquals("/client/homePage.jsp", modelAndView.getView());
        assertTrue(modelAndView.isRedirect());

        verify(reportService).uploadReport(any(ReportCreateDto.class));
        verify(reportService).xmlValidation(path);
        verify(part).write(path);
    }

    @Test
    public void uploadFileJson() throws IOException {
        String path = PATH + File.separator + FILE_NAME_JSON;

        when(part.getHeader("content-disposition")).thenReturn("form-data; name=\"part\"; filename=\"" + FILE_NAME_JSON + "\"");
        when(reportService.jsonValidation(path)).thenReturn(true);

        ModelAndView modelAndView = reportUploadController.uploadFile(request);
        assertNotNull(modelAndView);
        assertEquals("/client/homePage.jsp", modelAndView.getView());
        assertTrue(modelAndView.isRedirect());

        verify(reportService).uploadReport(any(ReportCreateDto.class));
        verify(reportService).jsonValidation(path);
        verify(part).write(path);
    }

    @Test(expected = AppException.class)
    public void uploadFileThrowExceptionWhenUnallowableFileType() {

        when(part.getHeader("content-disposition")).thenReturn("form-data; name=\"part\"; filename=\"" + WRONG_FILE_NAME + "\"");
        reportUploadController.uploadFile(request);
    }

    @AfterClass
    public static void deleteDirectory() {
        FileUtils.deleteQuietly(new File(PATH));
    }
}
