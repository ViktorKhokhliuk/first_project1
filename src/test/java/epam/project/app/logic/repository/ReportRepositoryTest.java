package epam.project.app.logic.repository;

import epam.project.app.logic.entity.dto.ReportCreateDto;
import epam.project.app.logic.entity.dto.ReportUpdateDto;
import epam.project.app.logic.entity.report.Report;
import epam.project.app.logic.entity.report.ReportInfo;
import epam.project.app.logic.entity.report.ReportStatus;
import epam.project.app.logic.entity.user.Client;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.sql.DataSource;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReportRepositoryTest {

    @Mock
    private DataSource dataSource;
    @Mock
    private Connection connection;
    @Mock
    private ResultSet resultSet;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private Statement statement;

    @InjectMocks
    private ReportRepository reportRepository;

    private static final String INSERT_REPORT = "INSERT INTO report (title, path, clientId, status, info, date, type) VALUES (?,?,?,?,?,?,?);";
    private static final String UPDATE_STATUS_OF_REPORT = "update report set status = ?, info = ? where id = ?;";
    private static final String SELECT_REPORT_BY_CLIENT_ID = "select * from report where clientId = ? limit ?, 5;";
    private static final String SELECT_REPORT_BY_ID = "select * from report where id = ?;";
    private static final String DELETE_REPORT_BY_ID = "delete from report where id = ?;";
    private static final String SELECT_ALL_REPORTS = "select * from report join client on report.clientId=client.id join user on client.id=user.id limit ?, 5;";
    private static final String SELECT_COUNT_ALL_REPORTS = "select count(*) from report join client on report.clientId=client.id join user on client.id=user.id;";
    private static final String SELECT_COUNT_REPORTS_BY_CLIENT = "select count(*) from report where clientId = ?;";

    private static final String TYPE = "income tax";
    private static final Long ID = 1L;
    private static final String TITLE = "report.xml";
    private static final String STATUS = ReportStatus.SUBMITTED.getTitle();
    private static final String INFO = ReportInfo.PROCESS.getTitle();
    private static final Long CLIENT_ID1 = 1L;
    private static final Long CLIENT_ID2 = 2L;
    private static final String PATH = "tax-office\\\\webapp\\\\upload\\\\id0";
    private static final String DATE = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    private static final int INDEX = 0;
    private static final String LOGIN = "tony";
    private static final String NAME = "Tony";
    private static final String SURNAME = "Smith";
    private static final String ITN = "123456789";

    @Before
    public void setUp() throws SQLException {
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    public void getClientReportsByFilterParameters() throws SQLException {
        List<Report> expectedList = Arrays.asList(new Report(ID, TITLE, PATH, STATUS, INFO, DATE, TYPE, CLIENT_ID1), new Report(ID, TITLE, PATH, STATUS, INFO, DATE, TYPE, CLIENT_ID1));
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("clientId", CLIENT_ID1.toString());
        parameters.put("type", TYPE);
        String sql = "select * from report where clientId = '" + parameters.get("clientId") + "' and type = '" + parameters.get("type") + "' limit ?, 5;";

        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(ID);
        when(resultSet.getString("title")).thenReturn(TITLE);
        when(resultSet.getString("path")).thenReturn(PATH);
        when(resultSet.getString("status")).thenReturn(STATUS);
        when(resultSet.getString("info")).thenReturn(INFO);
        when(resultSet.getString("type")).thenReturn(TYPE);
        when(resultSet.getString("date")).thenReturn(DATE);
        when(resultSet.getLong("clientId")).thenReturn(CLIENT_ID1);

        List<Report> resultList = reportRepository.getClientReportsByFilterParameters(parameters, INDEX);
        assertNotNull(resultList);
        assertEquals(expectedList, resultList);

        verify(resultSet, times(3)).next();
        verify(connection).prepareStatement(sql);
        verify(connection).close();
    }

    @Test
    public void insertReport() throws SQLException {
        ReportCreateDto reportCreateDto = new ReportCreateDto(TITLE, PATH, CLIENT_ID1, TYPE);
        Report expectedReport = new Report(ID, TITLE, PATH, STATUS, INFO, DATE, TYPE, CLIENT_ID1);

        when(connection.prepareStatement(INSERT_REPORT, Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
        when(preparedStatement.execute()).thenReturn(true);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(1)).thenReturn(ID);

        Optional<Report> resultReport = reportRepository.insertReport(reportCreateDto);
        assertNotNull(resultReport);
        assertTrue(resultReport.isPresent());
        assertEquals(expectedReport, resultReport.get());

        verify(resultSet).next();
        verify(connection).prepareStatement(INSERT_REPORT, Statement.RETURN_GENERATED_KEYS);
        verify(connection).close();
    }

    @Test
    public void updateStatusOfReport() throws SQLException {
        ReportUpdateDto reportUpdateDto = new ReportUpdateDto();
        reportUpdateDto.setId(ID);
        reportUpdateDto.setStatus(ReportStatus.ACCEPTED.getTitle());
        reportUpdateDto.setInfo(ReportInfo.ACCEPT.getTitle());
        Report expectedReport = new Report(ID, TITLE, PATH, reportUpdateDto.getStatus(), reportUpdateDto.getInfo(), DATE, TYPE, CLIENT_ID1);

        when(connection.prepareStatement(SELECT_REPORT_BY_ID)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("title")).thenReturn(TITLE);
        when(resultSet.getString("path")).thenReturn(PATH);
        when(resultSet.getString("status")).thenReturn(STATUS);
        when(resultSet.getString("info")).thenReturn(INFO);
        when(resultSet.getString("type")).thenReturn(TYPE);
        when(resultSet.getString("date")).thenReturn(DATE);
        when(resultSet.getLong("clientId")).thenReturn(CLIENT_ID1);
        when(connection.prepareStatement(UPDATE_STATUS_OF_REPORT)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        Optional<Report> resultReport = reportRepository.updateStatusOfReport(reportUpdateDto);
        assertNotNull(resultReport);
        assertTrue(resultReport.isPresent());
        assertEquals(expectedReport, resultReport.get());

        verify(resultSet).next();
        verify(connection).prepareStatement(UPDATE_STATUS_OF_REPORT);
        verify(connection).prepareStatement(SELECT_REPORT_BY_ID);
        verify(connection, times(2)).close();
    }

    @Test
    public void getReportByIdWhenFoundReport() throws SQLException {
        Report expectedReport = new Report(ID, TITLE, PATH, STATUS, INFO, DATE, TYPE, CLIENT_ID1);

        when(connection.prepareStatement(SELECT_REPORT_BY_ID)).thenReturn(preparedStatement);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("title")).thenReturn(TITLE);
        when(resultSet.getString("path")).thenReturn(PATH);
        when(resultSet.getString("status")).thenReturn(STATUS);
        when(resultSet.getString("info")).thenReturn(INFO);
        when(resultSet.getString("type")).thenReturn(TYPE);
        when(resultSet.getString("date")).thenReturn(DATE);
        when(resultSet.getLong("clientId")).thenReturn(CLIENT_ID1);

        Optional<Report> resultReport = reportRepository.getReportById(ID);
        assertNotNull(resultReport);
        assertTrue(resultReport.isPresent());
        assertEquals(expectedReport, resultReport.get());

        verify(connection).prepareStatement(SELECT_REPORT_BY_ID);
        verify(resultSet).next();
        verify(connection).close();
    }

    @Test
    public void getReportByIdWhenNotFoundReport() throws SQLException {
        when(connection.prepareStatement(SELECT_REPORT_BY_ID)).thenReturn(preparedStatement);
        when(resultSet.next()).thenReturn(false);

        Optional<Report> resultReport = reportRepository.getReportById(ID);
        assertNotNull(resultReport);
        assertFalse(resultReport.isPresent());

        verify(connection).prepareStatement(SELECT_REPORT_BY_ID);
        verify(resultSet).next();
        verify(connection).close();
    }

    @Test
    public void getAllReportsByClientIdWhenFoundReports() throws SQLException {
        List<Report> expectedList = Arrays.asList(new Report(ID, TITLE, PATH, STATUS, INFO, DATE, TYPE, CLIENT_ID1), new Report(ID, TITLE, PATH, STATUS, INFO, DATE, TYPE, CLIENT_ID1));

        when(connection.prepareStatement(SELECT_REPORT_BY_CLIENT_ID)).thenReturn(preparedStatement);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(ID);
        when(resultSet.getString("title")).thenReturn(TITLE);
        when(resultSet.getString("path")).thenReturn(PATH);
        when(resultSet.getString("status")).thenReturn(STATUS);
        when(resultSet.getString("info")).thenReturn(INFO);
        when(resultSet.getString("type")).thenReturn(TYPE);
        when(resultSet.getString("date")).thenReturn(DATE);

        List<Report> resultList = reportRepository.getAllReportsByClientId(CLIENT_ID1, INDEX);
        assertNotNull(resultList);
        assertEquals(expectedList, resultList);

        verify(resultSet, times(3)).next();
        verify(connection).prepareStatement(SELECT_REPORT_BY_CLIENT_ID);
        verify(connection).close();
    }

    @Test
    public void getAllReportsByClientIdWhenNotFoundReports() throws SQLException {
        List<Report> expectedList = Collections.emptyList();

        when(connection.prepareStatement(SELECT_REPORT_BY_CLIENT_ID)).thenReturn(preparedStatement);
        when(resultSet.next()).thenReturn(false);

        List<Report> resultList = reportRepository.getAllReportsByClientId(CLIENT_ID1, INDEX);
        assertNotNull(resultList);
        assertEquals(expectedList, resultList);
        assertTrue(resultList.isEmpty());

        verify(connection).prepareStatement(SELECT_REPORT_BY_CLIENT_ID);
        verify(resultSet).next();
        verify(connection).close();
    }

    @Test
    public void getAllReports() throws SQLException {
        Report report1 = new Report(ID, TITLE, PATH, STATUS, INFO, DATE, TYPE, CLIENT_ID1);
        Report report2 = new Report(ID, TITLE, PATH, STATUS, INFO, DATE, TYPE, CLIENT_ID1);
        Report report3 = new Report(ID, TITLE, PATH, STATUS, INFO, DATE, TYPE, CLIENT_ID2);
        Client client1 = new Client(NAME, SURNAME, ITN);
        client1.setLogin(LOGIN);
        client1.setId(CLIENT_ID1);
        Client client2 = new Client(NAME, SURNAME, ITN);
        client2.setLogin(LOGIN);
        client2.setId(CLIENT_ID2);
        List<Report> expectedReports = Arrays.asList(report1, report2, report3);
        Map<Long, Client> expectedClients = Map.of(CLIENT_ID1, client1, CLIENT_ID2, client2);
        Map<List<Report>, Map<Long, Client>> expectedMap = Map.of(expectedReports, expectedClients);

        when(connection.prepareStatement(SELECT_ALL_REPORTS)).thenReturn(preparedStatement);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(ID);
        when(resultSet.getLong("clientId")).thenReturn(CLIENT_ID1).thenReturn(CLIENT_ID1).thenReturn(CLIENT_ID2);
        when(resultSet.getString("title")).thenReturn(TITLE);
        when(resultSet.getString("path")).thenReturn(PATH);
        when(resultSet.getString("status")).thenReturn(STATUS);
        when(resultSet.getString("info")).thenReturn(INFO);
        when(resultSet.getString("type")).thenReturn(TYPE);
        when(resultSet.getString("date")).thenReturn(DATE);
        when(resultSet.getString("login")).thenReturn(LOGIN);
        when(resultSet.getString("name")).thenReturn(NAME);
        when(resultSet.getString("surname")).thenReturn(SURNAME);
        when(resultSet.getString("itn")).thenReturn(ITN);

        Map<List<Report>, Map<Long, Client>> resultMap = reportRepository.getAllReports(INDEX);
        assertNotNull(resultMap);
        assertEquals(expectedMap, resultMap);

        verify(connection).prepareStatement(SELECT_ALL_REPORTS);
        verify(resultSet, times(4)).next();
        verify(connection).close();
    }

    @Test
    public void getAllReportsWhenNotFoundReports() throws SQLException {
        List<Report> expectedReports = Collections.emptyList();
        Map<Long, Client> expectedClients = Collections.emptyMap();
        Map<List<Report>, Map<Long, Client>> expectedMap = Map.of(expectedReports, expectedClients);

        when(connection.prepareStatement(SELECT_ALL_REPORTS)).thenReturn(preparedStatement);
        when(resultSet.next()).thenReturn(false);

        Map<List<Report>, Map<Long, Client>> resultMap = reportRepository.getAllReports(INDEX);
        assertNotNull(resultMap);
        assertEquals(expectedMap, resultMap);
        assertTrue(resultMap.entrySet().iterator().next().getValue().isEmpty());
        assertTrue(resultMap.entrySet().iterator().next().getKey().isEmpty());

        verify(connection).prepareStatement(SELECT_ALL_REPORTS);
        verify(resultSet).next();
        verify(connection).close();
    }

    @Test
    public void getAllReportsByFilterParameters() throws SQLException {
        Report report1 = new Report(ID, TITLE, PATH, STATUS, INFO, DATE, TYPE, CLIENT_ID1);
        Report report3 = new Report(ID, TITLE, PATH, STATUS, INFO, DATE, TYPE, CLIENT_ID2);
        Client client1 = new Client(NAME, SURNAME, ITN);
        client1.setLogin(LOGIN);
        client1.setId(CLIENT_ID1);
        Client client2 = new Client(NAME, SURNAME, ITN);
        client2.setLogin(LOGIN);
        client2.setId(CLIENT_ID2);
        List<Report> expectedReports = Arrays.asList(report1, report3);
        Map<Long, Client> expectedClients = Map.of(CLIENT_ID1, client1, CLIENT_ID2, client2);
        Map<List<Report>, Map<Long, Client>> expectedMap = Map.of(expectedReports, expectedClients);

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("type", TYPE);
        parameters.put("name", NAME);
        String sql = "select * from report join client on report.clientId=client.id join user on client.id=user.id where report.type = '"
                + parameters.get("type") + "' and client.name = '" + parameters.get("name") + "' limit ?, 5;";

        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(ID);
        when(resultSet.getLong("clientId")).thenReturn(CLIENT_ID1).thenReturn(CLIENT_ID2);
        when(resultSet.getString("title")).thenReturn(TITLE);
        when(resultSet.getString("path")).thenReturn(PATH);
        when(resultSet.getString("status")).thenReturn(STATUS);
        when(resultSet.getString("info")).thenReturn(INFO);
        when(resultSet.getString("type")).thenReturn(TYPE);
        when(resultSet.getString("date")).thenReturn(DATE);
        when(resultSet.getString("login")).thenReturn(LOGIN);
        when(resultSet.getString("name")).thenReturn(NAME);
        when(resultSet.getString("surname")).thenReturn(SURNAME);
        when(resultSet.getString("itn")).thenReturn(ITN);

        Map<List<Report>, Map<Long, Client>> resultMap = reportRepository.getAllReportsByFilterParameters(parameters, INDEX);
        assertNotNull(resultMap);
        assertEquals(expectedMap, resultMap);
        assertEquals(expectedMap.entrySet().iterator().next().getKey(), resultMap.entrySet().iterator().next().getKey());
        assertEquals(expectedMap.entrySet().iterator().next().getValue(), resultMap.entrySet().iterator().next().getValue());

        verify(connection).prepareStatement(sql);
        verify(resultSet, times(3)).next();
        verify(connection).close();
    }

    @Test
    public void updateStatusOfReportAfterEdit() throws SQLException {
        Report expectedReport = new Report(ID, TITLE, PATH, ReportStatus.EDITED.getTitle(), ReportInfo.EDIT.getTitle(), DATE, TYPE, CLIENT_ID1);

        when(connection.prepareStatement(SELECT_REPORT_BY_ID)).thenReturn(preparedStatement);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("title")).thenReturn(TITLE);
        when(resultSet.getString("path")).thenReturn(PATH);
        when(resultSet.getString("status")).thenReturn(STATUS);
        when(resultSet.getString("info")).thenReturn(INFO);
        when(resultSet.getString("type")).thenReturn(TYPE);
        when(resultSet.getString("date")).thenReturn(DATE);
        when(resultSet.getLong("clientId")).thenReturn(CLIENT_ID1);
        when(connection.prepareStatement(UPDATE_STATUS_OF_REPORT)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        Optional<Report> resultReport = reportRepository.updateStatusOfReportAfterEdit(ID);
        assertNotNull(resultReport);
        assertTrue(resultReport.isPresent());
        assertEquals(expectedReport, resultReport.get());

        verify(connection).prepareStatement(SELECT_REPORT_BY_ID);
        verify(connection).prepareStatement(UPDATE_STATUS_OF_REPORT);
        verify(resultSet).next();
        verify(connection, times(2)).close();
    }

    @Test
    public void deleteReportById() throws SQLException {
        Report expectedReport = new Report(ID, TITLE, PATH, STATUS, INFO, DATE, TYPE, CLIENT_ID1);

        when(connection.prepareStatement(SELECT_REPORT_BY_ID)).thenReturn(preparedStatement);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("title")).thenReturn(TITLE);
        when(resultSet.getString("path")).thenReturn(PATH);
        when(resultSet.getString("status")).thenReturn(STATUS);
        when(resultSet.getString("info")).thenReturn(INFO);
        when(resultSet.getString("type")).thenReturn(TYPE);
        when(resultSet.getString("date")).thenReturn(DATE);
        when(resultSet.getLong("clientId")).thenReturn(CLIENT_ID1);
        when(connection.prepareStatement(DELETE_REPORT_BY_ID)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        Optional<Report> resultReport = reportRepository.deleteReportById(ID);
        assertNotNull(resultReport);
        assertTrue(resultReport.isPresent());
        assertEquals(expectedReport, resultReport.get());

        verify(connection).prepareStatement(SELECT_REPORT_BY_ID);
        verify(connection).prepareStatement(DELETE_REPORT_BY_ID);
        verify(resultSet).next();
        verify(connection, times(2)).close();
    }

    @Test
    public void getCountOfPageForAllReports() throws SQLException {
        double expected = 11.0;

        when(statement.executeQuery(SELECT_COUNT_ALL_REPORTS)).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getDouble(1)).thenReturn(expected);

        double result = reportRepository.getCountOfFieldForAllReports();
        assertEquals(expected, result, 0.0);

        verify(connection).close();
    }

    @Test
    public void getCountOfPageForFilterReports() throws SQLException {
        double expected = 11.0;
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("type", TYPE);
        parameters.put("name", NAME);
        String sql = "select count(*) from report join client on report.clientId=client.id join user on client.id=user.id where report.type = '"
                + parameters.get("type") + "' and client.name = '" + parameters.get("name") + "';";

        when(statement.executeQuery(sql)).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getDouble(1)).thenReturn(expected);

        double result = reportRepository.getCountOfFieldForFilterReports(parameters);
        assertEquals(expected, result, 0.0);

        verify(connection).close();
    }

    @Test
    public void getCountOfPageForAllClientReports() throws SQLException {
        double expected = 11.0;

        when(connection.prepareStatement(SELECT_COUNT_REPORTS_BY_CLIENT)).thenReturn(preparedStatement);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getDouble(1)).thenReturn(expected);

        double result = reportRepository.getCountOfFieldForAllClientReports(CLIENT_ID1);
        assertEquals(expected, result, 0.0);

        verify(connection).prepareStatement(SELECT_COUNT_REPORTS_BY_CLIENT);
        verify(connection).close();
    }

    @Test
    public void getCountOfPageForFilterClientReports() throws SQLException {
        double expected = 11.0;
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("clientId", CLIENT_ID1.toString());
        parameters.put("type", TYPE);
        String sql = "select count(*) from report where clientId = '" + parameters.get("clientId") + "' and type = '" + parameters.get("type") + "';";

        when(statement.executeQuery(sql)).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getDouble(1)).thenReturn(expected);

        double result = reportRepository.getCountOfFieldForFilterClientReports(parameters);
        assertEquals(expected, result, 0.0);

        verify(connection).close();
    }
}
