package epam.project.app.logic.service;

import epam.project.app.logic.builder.Building;
import epam.project.app.logic.entity.dto.ReportCreateDto;
import epam.project.app.logic.entity.dto.ReportEditDto;
import epam.project.app.logic.entity.dto.ReportUpdateDto;
import epam.project.app.logic.entity.report.Report;
import epam.project.app.logic.entity.report.ReportInfo;
import epam.project.app.logic.entity.report.ReportParameters;
import epam.project.app.logic.entity.report.ReportStatus;
import epam.project.app.logic.entity.user.Client;
import epam.project.app.logic.exception.ReportException;
import epam.project.app.logic.parser.Parsing;
import epam.project.app.logic.repository.ReportRepository;
import epam.project.app.logic.validator.Validating;
import jakarta.servlet.http.Part;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReportServiceTest {

    private final ReportRepository reportRepository = mock(ReportRepository.class);
    private final Map<String, Validating> validators = Map.of(".xml", mock(Validating.class));
    private final Map<String, Parsing> parsers = Map.of(".xml", mock(Parsing.class));
    private final Map<String, Building> builders = Map.of(".xml", mock(Building.class));

    private final ReportService reportService = new ReportService(reportRepository, validators, parsers, builders);

    private static final String TITLE = "report.xml";
    private static final String WRONG_FILE_EXTENSION = "report.txt";
    private static final String PATH = "tax-office\\webapp\\upload\\id0";
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
    public void validateFile() throws IOException {
        String path = PATH + File.separator + TITLE;
        Part part = mock(Part.class);
        boolean expected = true;
        when(validators.get(".xml").validate(path)).thenReturn(expected);

        boolean result = reportService.validateFile(part, path);

        assertEquals(expected, result);
        verify(part).write(path);
    }

    @Test(expected = ReportException.class)
    public void validateFileThrowExceptionWhenWrongFileExtension() {
        String path = PATH + File.separator + WRONG_FILE_EXTENSION;
        Part part = mock(Part.class);

        reportService.validateFile(part, path);
    }

    @Test
    public void getReportParameters() {
        String path = PATH + File.separator + TITLE;
        ReportParameters expectedReportParameters = new ReportParameters("natural", "ukrainian", "2022", "2", "10", "IV", "Programmer", "10000");
        when(parsers.get(".xml").parse(path)).thenReturn(expectedReportParameters);

        ReportParameters resultReportParameters = reportService.getReportParameters(path);
        assertNotNull(resultReportParameters);
        assertEquals(expectedReportParameters, resultReportParameters);
    }

    @Test
    public void saveReportChangesXml() {
        String path = PATH + File.separator + TITLE;
        ReportEditDto reportEditDto = new ReportEditDto();
        boolean expected = true;

        when(builders.get(".xml").build(reportEditDto, path)).thenReturn(true);

        boolean result = reportService.saveReportChanges(reportEditDto, path);
        assertEquals(expected, result);
    }

    @Test
    public void getCountOfPageForAllClientReports() {
        double expected = 3.0;

        when(reportRepository.getCountOfFieldForAllClientReports(CLIENT_ID)).thenReturn(11.0);

        double result = reportService.getCountOfPageForAllClientReports(CLIENT_ID);
        assertEquals(expected, result, 0.0);

        verify(reportRepository).getCountOfFieldForAllClientReports(CLIENT_ID);
    }

    @Test
    public void getCountOfPageForFilterClientReports() {
        Map<String, String> parameters = Map.of("type", TYPE, "clientId", CLIENT_ID.toString());
        double expected = 3.0;

        when(reportRepository.getCountOfFieldForFilterClientReports(parameters)).thenReturn(11.0);

        double result = reportService.getCountOfPageForFilterClientReports(parameters);
        assertEquals(expected, result, 0.0);

        verify(reportRepository).getCountOfFieldForFilterClientReports(parameters);

    }

    @Test
    public void getCountOfPageForAllReports() {
        double expected = 4.0;

        when(reportRepository.getCountOfFieldForAllReports()).thenReturn(17.0);

        double result = reportService.getCountOfPageForAllReports();
        assertEquals(expected, result, 0.0);

        verify(reportRepository).getCountOfFieldForAllReports();

    }

    @Test
    public void getCountOfPageForFilterReports() {
        Map<String, String> parameters = Map.of("type", TYPE, "date", DATE);
        double expected = 4.0;

        when(reportRepository.getCountOfFieldForFilterReports(parameters)).thenReturn(17.0);

        double result = reportService.getCountOfPageForFilterReports(parameters);
        assertEquals(expected, result, 0.0);

        verify(reportRepository).getCountOfFieldForFilterReports(parameters);
    }
}
