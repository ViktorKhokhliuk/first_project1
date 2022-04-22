package epam.project.app.logic.controller;

import epam.project.app.infra.web.ModelAndView;
import epam.project.app.infra.web.QueryParameterResolver;
import epam.project.app.logic.entity.dto.ReportUpdateDto;
import epam.project.app.logic.entity.report.Report;
import epam.project.app.logic.entity.report.ReportParameters;
import epam.project.app.logic.entity.report.ReportStatus;
import epam.project.app.logic.entity.user.Client;
import epam.project.app.logic.entity.user.Inspector;
import epam.project.app.logic.entity.user.User;
import epam.project.app.logic.entity.user.UserRole;
import epam.project.app.logic.service.ReportService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReportControllerTest {

    @Mock
    private ReportService reportService;
    @Mock
    private QueryParameterResolver queryParameterResolver;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;

    @InjectMocks
    private ReportController reportController;

    private static final Long ID = 0L;
    private static final Long CLIENT_ID = 0L;
    private static final String TITLE_XML = "report.xml";
    private static final Integer PAGE = 1;
    private static final String CLIENT_LOGIN = "tony";
    private static final String DATE = "2022-01-01";
    private static final String TYPE = "income tax";

    @Test
    public void updateStatusOfReport() {
        User user = new Inspector();
        user.setUserRole(UserRole.INSPECTOR);
        Report report = new Report();
        ReportUpdateDto reportUpdateDto = new ReportUpdateDto();

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(queryParameterResolver.getObject(request, ReportUpdateDto.class)).thenReturn(reportUpdateDto);
        when(reportService.updateStatusOfReport(reportUpdateDto)).thenReturn(report);

        ModelAndView modelAndView = reportController.updateStatusOfReport(request);
        assertNotNull(modelAndView);
        assertTrue(modelAndView.isRedirect());
        assertEquals("/service/filterAllReports", modelAndView.getView());

        verify(request).getSession(false);
        verify(session).getAttribute("user");
        verify(reportService).updateStatusOfReport(reportUpdateDto);
    }

    @Test
    public void deleteReportById() {
        User user = new Client();
        Report report = new Report();
        ReportUpdateDto reportUpdateDto = new ReportUpdateDto();
        reportUpdateDto.setClientId(CLIENT_ID);
        reportUpdateDto.setTitle(TITLE_XML);
        reportUpdateDto.setId(ID);

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(queryParameterResolver.getObject(request, ReportUpdateDto.class)).thenReturn(reportUpdateDto);
        when(reportService.deleteReportById(ID)).thenReturn(report);

        ModelAndView modelAndView = reportController.deleteReportById(request);
        assertNotNull(modelAndView);
        assertTrue(modelAndView.isRedirect());
        assertEquals("/service/filterClientReports", modelAndView.getView());
        assertEquals(CLIENT_ID, modelAndView.getAttributes().get("clientId"));

        verify(request).getSession(false);
        verify(session).getAttribute("user");
        verify(reportService).deleteReportById(ID);
    }

    @Test
    public void getAllReportsByClientId() {
        List<Report> expectedReports = Arrays.asList(new Report(), new Report(), new Report());
        double countOfPage = 1.0;
        User user = new Inspector();
        user.setUserRole(UserRole.INSPECTOR);

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("page")).thenReturn(PAGE.toString());
        when(request.getParameter("clientId")).thenReturn(CLIENT_ID.toString());
        when(request.getParameter("clientLogin")).thenReturn(CLIENT_LOGIN);
        when(reportService.getAllReportsByClientId(CLIENT_ID, PAGE)).thenReturn(expectedReports);
        when(reportService.getCountOfPageForAllClientReports(CLIENT_ID)).thenReturn(countOfPage);

        ModelAndView modelAndView = reportController.getAllReportsByClientId(request);
        assertNotNull(modelAndView);
        assertFalse(modelAndView.isRedirect());
        assertEquals("/inspector/reportsByClientId.jsp", modelAndView.getView());
        assertEquals(PAGE, modelAndView.getAttributes().get("page"));
        assertEquals(countOfPage, modelAndView.getAttributes().get("countOfPage"));
        assertEquals(expectedReports, modelAndView.getAttributes().get("reports"));
        assertEquals(CLIENT_ID, modelAndView.getAttributes().get("clientId"));
        assertEquals(CLIENT_LOGIN, modelAndView.getAttributes().get("clientLogin"));

        verify(request).getSession(false);
        verify(session).getAttribute("user");
        verify(reportService).getAllReportsByClientId(CLIENT_ID, PAGE);
        verify(reportService).getCountOfPageForAllClientReports(CLIENT_ID);
    }

    @Test
    public void getAllReports() {
        double countOfPage = 1.0;
        Report report1 = new Report();
        report1.setDate(DATE);
        Report report2 = new Report();
        report2.setDate(DATE);

        List<Report> expectedReports = Arrays.asList(report1, report2);
        Map<Long, Client> expectedClients = Map.of(1L, new Client(), 2L, new Client());
        Map<List<Report>, Map<Long, Client>> expectedMap = Map.of(expectedReports, expectedClients);

        when(request.getParameter("page")).thenReturn(PAGE.toString());
        when(reportService.getAllReports(PAGE)).thenReturn(expectedMap);
        when(reportService.getCountOfPageForAllReports()).thenReturn(countOfPage);

        ModelAndView modelAndView = reportController.getAllReports(request);
        assertNotNull(modelAndView);
        assertFalse(modelAndView.isRedirect());
        assertEquals("/inspector/allReports.jsp", modelAndView.getView());
        assertEquals(countOfPage, modelAndView.getAttributes().get("countOfPage"));
        assertEquals(PAGE, modelAndView.getAttributes().get("page"));
        assertEquals(expectedClients, modelAndView.getAttributes().get("clients"));
        assertEquals(expectedReports, modelAndView.getAttributes().get("reports"));

        verify(reportService).getAllReports(PAGE);
        verify(reportService).getCountOfPageForAllReports();
    }

    @Test
    public void getClientReportsByFilterParameters() {
        User user = new Client();
        double countOfPage = 1.0;
        Report report1 = new Report();
        report1.setDate(DATE);
        Report report2 = new Report();
        report2.setDate(DATE);
        List<Report> expectedReports = Arrays.asList(report1, report2);

        Vector<String> names = new Vector<>();
        names.add("type");
        names.add("status");
        Enumeration<String> parameterNames = names.elements();

        Map<String, String> parameters = new HashMap<>();
        parameters.put(names.get(0), TYPE);
        parameters.put(names.get(1), ReportStatus.SUBMITTED.getTitle());

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("page")).thenReturn(PAGE.toString());
        when(request.getParameterNames()).thenReturn(parameterNames);
        when(request.getParameter("type")).thenReturn(TYPE);
        when(request.getParameter("status")).thenReturn(ReportStatus.SUBMITTED.getTitle());
        when(reportService.getClientReportsByFilterParameters(parameters, PAGE)).thenReturn(expectedReports);
        when(reportService.getCountOfPageForFilterClientReports(parameters)).thenReturn(countOfPage);

        ModelAndView modelAndView = reportController.getClientReportsByFilterParameters(request);
        assertNotNull(modelAndView);
        assertFalse(modelAndView.isRedirect());
        assertEquals("/client/reports.jsp", modelAndView.getView());
        assertEquals(countOfPage, modelAndView.getAttributes().get("countOfPage"));
        assertEquals(PAGE, modelAndView.getAttributes().get("page"));
        assertEquals(expectedReports, modelAndView.getAttributes().get("reports"));

        verify(request).getSession(false);
        verify(session).getAttribute("user");
        verify(request).getParameterNames();
        verify(reportService).getClientReportsByFilterParameters(parameters, PAGE);
        verify(reportService).getCountOfPageForFilterClientReports(parameters);
    }

    @Test
    public void getAllReportsByFilterParameters() {
        double countOfPage = 1.0;
        Report report1 = new Report();
        report1.setDate(DATE);
        Report report2 = new Report();
        report2.setDate(DATE);

        List<Report> expectedReports = Arrays.asList(report1, report2);
        Map<Long, Client> expectedClients = Map.of(1L, new Client(), 2L, new Client());
        Map<List<Report>, Map<Long, Client>> expectedMap = Map.of(expectedReports, expectedClients);

        Vector<String> names = new Vector<>();
        names.add("type");
        names.add("status");
        Enumeration<String> parameterNames = names.elements();

        Map<String, String> parameters = new HashMap<>();
        parameters.put(names.get(0), TYPE);
        parameters.put(names.get(1), ReportStatus.SUBMITTED.getTitle());

        when(request.getParameterNames()).thenReturn(parameterNames);
        when(request.getParameter("page")).thenReturn(PAGE.toString());
        when(request.getParameter("type")).thenReturn(TYPE);
        when(request.getParameter("status")).thenReturn(ReportStatus.SUBMITTED.getTitle());
        when(reportService.getAllReportsByFilterParameters(parameters, PAGE)).thenReturn(expectedMap);
        when(reportService.getCountOfPageForFilterReports(parameters)).thenReturn(countOfPage);

        ModelAndView modelAndView = reportController.getAllReportsByFilterParameters(request);
        assertNotNull(modelAndView);
        assertFalse(modelAndView.isRedirect());
        assertEquals("/inspector/allReports.jsp", modelAndView.getView());
        assertEquals(countOfPage, modelAndView.getAttributes().get("countOfPage"));
        assertEquals(PAGE, modelAndView.getAttributes().get("page"));
        assertEquals(expectedClients, modelAndView.getAttributes().get("clients"));
        assertEquals(expectedReports, modelAndView.getAttributes().get("reports"));

        verify(request).getParameterNames();
        verify(reportService).getAllReportsByFilterParameters(parameters, PAGE);
        verify(reportService).getCountOfPageForFilterReports(parameters);
    }

    @Test
    public void showReport() {
        ReportParameters expectedReportParameters = new ReportParameters("natural", "ukrainian", "2022", "2", "10", "II", "programmer", "2000");
        String path = "webapp/upload/id" + CLIENT_ID + "/" + TITLE_XML;
        when(request.getParameter("clientId")).thenReturn(CLIENT_ID.toString());
        when(request.getParameter("title")).thenReturn(TITLE_XML);
        when(reportService.getReportParameters(path)).thenReturn(expectedReportParameters);

        ModelAndView modelAndView = reportController.showReport(request);
        assertNotNull(modelAndView);
        assertFalse(modelAndView.isRedirect());
        assertEquals("/user/report.jsp", modelAndView.getView());
        assertEquals(expectedReportParameters, modelAndView.getAttributes().get("reportParameters"));

        verify(reportService).getReportParameters(path);
    }
}
