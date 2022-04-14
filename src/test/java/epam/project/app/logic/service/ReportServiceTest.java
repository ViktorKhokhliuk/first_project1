package epam.project.app.logic.service;

import epam.project.app.logic.entity.dto.ReportCreateDto;
import epam.project.app.logic.entity.dto.ReportEditDto;
import epam.project.app.logic.entity.dto.ReportUpdateDto;
import epam.project.app.logic.entity.report.Report;
import epam.project.app.logic.entity.report.ReportInfo;
import epam.project.app.logic.entity.report.ReportParameters;
import epam.project.app.logic.entity.report.ReportStatus;
import epam.project.app.logic.entity.user.Client;
import epam.project.app.logic.entity.validate.FileValidator;
import epam.project.app.logic.exception.ReportException;
import epam.project.app.logic.parse.JsonBuilder;
import epam.project.app.logic.parse.JsonParser;
import epam.project.app.logic.parse.XmlBuilder;
import epam.project.app.logic.parse.XmlParser;
import epam.project.app.logic.repository.ReportRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReportServiceTest {

    @Mock
    private ReportRepository reportRepository;
    @Mock
    private FileValidator fileValidator;
    @Mock
    private XmlParser xmlParser;
    @Mock
    private XmlBuilder xmlBuilder;
    @Mock
    private JsonParser jsonParser;
    @Mock
    private JsonBuilder jsonBuilder;

    @InjectMocks
    private ReportService reportService;

    private static final String TITLE = "report.xml";
    private static final String PATH = "tax-office\\\\webapp\\\\upload\\\\id0";
    private static final Long CLIENT_ID = 1L;
    private static final Long ID = 1L;
    private static final String TYPE = "income tax";
    private static final String DATE = "2022-01-01";

    @Test
    public void uploadReport() {
        Report expectedReport = new Report(ID, TITLE, PATH, ReportStatus.SUBMITTED.getTitle(), ReportInfo.PROCESS.getTitle(), DATE, TYPE, CLIENT_ID);
        ReportCreateDto reportCreateDto = new ReportCreateDto(TITLE, PATH, CLIENT_ID, TYPE);

        when(reportRepository.insertReport(reportCreateDto)).thenReturn(Optional.of(expectedReport));
        Report resultReport = reportService.uploadReport(reportCreateDto);

        assertNotNull(resultReport);
        assertEquals(expectedReport, resultReport);
        verify(reportRepository).insertReport(reportCreateDto);
    }

    @Test(expected = ReportException.class)
    public void uploadReportThrowExceptionWhenRepositoryReturnOptionalEmpty() {
        ReportCreateDto reportCreateDto = new ReportCreateDto(TITLE, PATH, CLIENT_ID, TYPE);

        when(reportRepository.insertReport(reportCreateDto)).thenReturn(Optional.empty());
        reportService.uploadReport(reportCreateDto);
    }

    @Test
    public void updateStatusOfReport() {
        Report expectedReport = new Report(ID, TITLE, PATH, ReportStatus.ACCEPTED.getTitle(), ReportInfo.ACCEPT.getTitle(), DATE, TYPE, CLIENT_ID);
        ReportUpdateDto reportUpdateDto = new ReportUpdateDto();

        when(reportRepository.updateStatusOfReport(reportUpdateDto)).thenReturn(Optional.of(expectedReport));
        Report resultReport = reportService.updateStatusOfReport(reportUpdateDto);

        assertNotNull(resultReport);
        assertEquals(expectedReport, resultReport);
        verify(reportRepository).updateStatusOfReport(reportUpdateDto);
    }

    @Test(expected = ReportException.class)
    public void updateStatusOfReportThrowExceptionWhenRepositoryReturnOptionalEmpty() {
        ReportUpdateDto reportUpdateDto = new ReportUpdateDto();

        when(reportRepository.updateStatusOfReport(reportUpdateDto)).thenReturn(Optional.empty());
        reportService.updateStatusOfReport(reportUpdateDto);
    }

    @Test
    public void updateStatusOfReportAfterEdit() {
        Report expectedReport = new Report(ID, TITLE, PATH, ReportStatus.EDITED.getTitle(), ReportInfo.EDIT.getTitle(), DATE, TYPE, CLIENT_ID);

        when(reportRepository.updateStatusOfReportAfterEdit(ID)).thenReturn(Optional.of(expectedReport));
        Report resultReport = reportService.updateStatusOfReportAfterEdit(ID);

        assertNotNull(resultReport);
        assertEquals(expectedReport, resultReport);
        verify(reportRepository).updateStatusOfReportAfterEdit(ID);
    }

    @Test(expected = ReportException.class)
    public void updateStatusOfReportAfterEditThrowExceptionWhenRepositoryReturnOptionalEmpty() {

        when(reportRepository.updateStatusOfReportAfterEdit(ID)).thenReturn(Optional.empty());
        reportService.updateStatusOfReportAfterEdit(ID);
    }

    @Test
    public void deleteReportById() {
        Report expectedReport = new Report(ID, TITLE, PATH, ReportStatus.EDITED.getTitle(), ReportInfo.EDIT.getTitle(), DATE, TYPE, CLIENT_ID);

        when(reportRepository.deleteReportById(ID)).thenReturn(Optional.of(expectedReport));
        Report resultReport = reportService.deleteReportById(ID);

        assertNotNull(resultReport);
        assertEquals(expectedReport, resultReport);
        verify(reportRepository).deleteReportById(ID);
    }

    @Test(expected = ReportException.class)
    public void deleteReportByIdThrowExceptionWhenRepositoryReturnOptionalEmpty() {

        when(reportRepository.deleteReportById(ID)).thenReturn(Optional.empty());
        reportService.deleteReportById(ID);
    }

    @Test
    public void getAllReportsByClientId() {
        List<Report> expectedReports = Arrays.asList(new Report(ID, TITLE, PATH, ReportStatus.SUBMITTED.getTitle(), ReportInfo.PROCESS.getTitle(), DATE, TYPE, CLIENT_ID),
                new Report(ID, TITLE, PATH, ReportStatus.EDITED.getTitle(), ReportInfo.EDIT.getTitle(), DATE, TYPE, CLIENT_ID));

        when(reportRepository.getAllReportsByClientId(CLIENT_ID, 0)).thenReturn(expectedReports);

        List<Report> resultReports = reportService.getAllReportsByClientId(CLIENT_ID, 1);
        assertNotNull(resultReports);
        assertEquals(expectedReports, resultReports);

        verify(reportRepository).getAllReportsByClientId(CLIENT_ID, 0);
    }

    @Test
    public void getClientReportsByFilterParameters() {
        List<Report> expectedReports = Arrays.asList(new Report(ID, TITLE, PATH, ReportStatus.SUBMITTED.getTitle(), ReportInfo.PROCESS.getTitle(), DATE, TYPE, CLIENT_ID),
                new Report(ID, TITLE, PATH, ReportStatus.SUBMITTED.getTitle(), ReportInfo.PROCESS.getTitle(), DATE, TYPE, CLIENT_ID));
        Map<String, String> parameters = Map.of("status", ReportStatus.SUBMITTED.getTitle(), "clientId", CLIENT_ID.toString());

        when(reportRepository.getClientReportsByFilterParameters(parameters, 0)).thenReturn(expectedReports);
        List<Report> resultReports = reportService.getClientReportsByFilterParameters(parameters, 1);
        assertNotNull(resultReports);
        assertEquals(expectedReports, resultReports);

        verify(reportRepository).getClientReportsByFilterParameters(parameters, 0);
    }

    @Test
    public void getAllReports() {
        List<Report> expectedReports = Arrays.asList(new Report(), new Report(), new Report());
        Map<Long, Client> expectedClients = Map.of(1L, new Client(), 2L, new Client());
        Map<List<Report>, Map<Long, Client>> expectedMap = Map.of(expectedReports, expectedClients);

        when(reportRepository.getAllReports(0)).thenReturn(expectedMap);
        Map<List<Report>, Map<Long, Client>> resultMap = reportService.getAllReports(1);
        assertNotNull(resultMap);
        assertEquals(expectedMap, resultMap);
        assertFalse(resultMap.isEmpty());

        verify(reportRepository).getAllReports(0);
    }

    @Test
    public void getAllReportsByFilterParameters() {
        Report report1 = new Report();
        report1.setType(TYPE);
        report1.setClientId(CLIENT_ID);
        Report report2 = new Report();
        report2.setType(TYPE);
        report2.setClientId(CLIENT_ID);

        List<Report> expectedReports = Arrays.asList(report1, report2);
        Map<Long, Client> expectedClients = Map.of(ID, new Client());
        Map<List<Report>, Map<Long, Client>> expectedMap = Map.of(expectedReports, expectedClients);
        Map<String, String> parameters = Map.of("type", TYPE, "clientId", CLIENT_ID.toString());

        when(reportRepository.getAllReportsByFilterParameters(parameters, 0)).thenReturn(expectedMap);
        Map<List<Report>, Map<Long, Client>> resultMap = reportService.getAllReportsByFilterParameters(parameters, 1);
        assertNotNull(resultMap);
        assertEquals(expectedMap, resultMap);
        assertFalse(resultMap.isEmpty());

        verify(reportRepository).getAllReportsByFilterParameters(parameters, 0);
    }

    @Test
    public void xmlValidation() {
        boolean expected = true;
        when(fileValidator.xmlFileValidation(PATH)).thenReturn(expected);

        boolean result = reportService.xmlValidation(PATH);

        assertEquals(expected, result);
        verify(fileValidator).xmlFileValidation(PATH);
    }

    @Test
    public void jsonValidation() {
        boolean expected = true;
        when(fileValidator.jsonFileValidation(PATH)).thenReturn(expected);

        boolean result = reportService.jsonValidation(PATH);

        assertEquals(expected, result);
        verify(fileValidator).jsonFileValidation(PATH);
    }

    @Test
    public void getReportParametersXml() {
        ReportParameters expectedReportParameters = new ReportParameters("natural", "ukrainian", "2022", "2", "10", "II", "programmer", "2000");

        when(xmlParser.parse(PATH)).thenReturn(expectedReportParameters);

        ReportParameters resultReportParameters = reportService.getReportParametersXml(PATH);
        assertNotNull(resultReportParameters);
        assertEquals(expectedReportParameters, resultReportParameters);

        verify(xmlParser).parse(PATH);
    }

    @Test
    public void getReportParametersJson() {
        ReportParameters expectedReportParameters = new ReportParameters("natural", "ukrainian", "2022", "2", "10", "II", "programmer", "2000");

        when(jsonParser.parse(PATH)).thenReturn(expectedReportParameters);

        ReportParameters resultReportParameters = reportService.getReportParametersJson(PATH);
        assertNotNull(resultReportParameters);
        assertEquals(expectedReportParameters, resultReportParameters);

        verify(jsonParser).parse(PATH);
    }

    @Test
    public void saveReportChangesXml() {
        ReportEditDto reportEditDto = new ReportEditDto();
        boolean expected = true;

        when(xmlBuilder.buildXml(reportEditDto, PATH)).thenReturn(true);

        boolean result = reportService.saveReportChangesXml(reportEditDto, PATH);
        assertEquals(expected, result);
        verify(xmlBuilder).buildXml(reportEditDto, PATH);
    }

    @Test
    public void saveReportChangesJson() {
        ReportEditDto reportEditDto = new ReportEditDto();
        boolean expected = true;

        when(jsonBuilder.buildJson(reportEditDto, PATH)).thenReturn(true);

        boolean result = reportService.saveReportChangesJson(reportEditDto, PATH);
        assertEquals(expected, result);
        verify(jsonBuilder).buildJson(reportEditDto, PATH);
    }

    @Test
    public void getCountOfPageForAllClientReports() {
        double expected = 3.0;

        when(reportRepository.getCountOfPageForAllClientReports(CLIENT_ID)).thenReturn(11.0);

        double result = reportService.getCountOfPageForAllClientReports(CLIENT_ID);
        assertEquals(expected, result, 0.0);

        verify(reportRepository).getCountOfPageForAllClientReports(CLIENT_ID);
    }

    @Test
    public void getCountOfPageForFilterClientReports() {
        Map<String, String> parameters = Map.of("type", TYPE, "clientId", CLIENT_ID.toString());
        double expected = 3.0;

        when(reportRepository.getCountOfPageForFilterClientReports(parameters)).thenReturn(11.0);

        double result = reportService.getCountOfPageForFilterClientReports(parameters);
        assertEquals(expected, result, 0.0);

        verify(reportRepository).getCountOfPageForFilterClientReports(parameters);

    }

    @Test
    public void getCountOfPageForAllReports() {
        double expected = 4.0;

        when(reportRepository.getCountOfPageForAllReports()).thenReturn(17.0);

        double result = reportService.getCountOfPageForAllReports();
        assertEquals(expected, result, 0.0);

        verify(reportRepository).getCountOfPageForAllReports();

    }

    @Test
    public void getCountOfPageForFilterReports() {
        Map<String, String> parameters = Map.of("type", TYPE, "date", DATE);
        double expected = 4.0;

        when(reportRepository.getCountOfPageForFilterReports(parameters)).thenReturn(17.0);

        double result = reportService.getCountOfPageForFilterReports(parameters);
        assertEquals(expected, result, 0.0);

        verify(reportRepository).getCountOfPageForFilterReports(parameters);
    }
}
