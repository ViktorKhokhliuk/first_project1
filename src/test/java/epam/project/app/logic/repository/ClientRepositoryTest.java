package epam.project.app.logic.repository;

import epam.project.app.logic.entity.dto.ClientRegistrationDto;
import epam.project.app.logic.entity.user.Client;
import epam.project.app.logic.entity.user.UserRole;
import epam.project.app.logic.exception.ClientException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClientRepositoryTest {

    @Mock
    private DataSource dataSource;
    @Mock
    private Connection connection;
    @Mock
    private ResultSet resultSet;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private PreparedStatement preparedStatement1;
    @Mock
    private PreparedStatement preparedStatement2;
    @Mock
    private Statement statement;

    @InjectMocks
    private ClientRepository clientRepository;

    private static final String INSERT_CLIENT = "INSERT INTO client (id,name,surname,itn) VALUES (?,?,?,?);";
    private static final String INSERT_USER = "INSERT INTO user (login,password,role) VALUES (?,?,?);";
    private static final String SELECT_CLIENT_BY_ID = "select * from user join client on user.id=client.id where user.id= ?;";
    private static final String SELECT_ALL_CLIENTS = "select * from client join user on user.id=client.id limit ?, 5;";
    private static final String DELETE_REPORTS_BY_CLIENT_ID = "delete from report where clientId = ?;";
    private static final String DELETE_CLIENT_BY_ID = "delete from client where id = ?;";
    private static final String DELETE_USER_BY_ID = "delete from user where id = ?;";
    private static final String SELECT_COUNT = "select count(*) from client join user on user.id=client.id;";
    private static final Long ID = 0L;
    private static final String LOGIN = "tony";
    private static final String PASSWORD = "tony123";
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
    public void getClientByIdWhenFoundClient() throws SQLException {
        Client expectedClient = new Client(NAME, SURNAME, ITN);
        expectedClient.setId(ID);
        expectedClient.setLogin(LOGIN);

        when(connection.prepareStatement(SELECT_CLIENT_BY_ID)).thenReturn(preparedStatement);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("name")).thenReturn(NAME);
        when(resultSet.getString("surname")).thenReturn(SURNAME);
        when(resultSet.getString("itn")).thenReturn(ITN);
        when(resultSet.getString("login")).thenReturn(LOGIN);

        Optional<Client> resultClient = clientRepository.getClientById(ID);
        assertNotNull(resultClient);
        assertTrue(resultClient.isPresent());
        assertEquals(expectedClient, resultClient.get());

        verify(preparedStatement).setLong(1, ID);
        verify(connection).prepareStatement(SELECT_CLIENT_BY_ID);

    }

    @Test
    public void getClientByIdWhenNotFoundClient() throws SQLException {
        when(connection.prepareStatement(SELECT_CLIENT_BY_ID)).thenReturn(preparedStatement);
        when(resultSet.next()).thenReturn(false);

        Optional<Client> resultClient = clientRepository.getClientById(ID);
        assertNotNull(resultClient);
        assertFalse(resultClient.isPresent());

        verify(preparedStatement).setLong(1, ID);
        verify(connection).prepareStatement(SELECT_CLIENT_BY_ID);
        verify(resultSet).next();
    }

    @Test
    public void getAllClientsWhenFoundClients() throws SQLException {
        Client expectedClient1 = new Client(NAME, SURNAME, ITN);
        expectedClient1.setId(ID);
        expectedClient1.setLogin(LOGIN);
        Client expectedClient2 = new Client(NAME, SURNAME, ITN);
        expectedClient2.setId(ID);
        expectedClient2.setLogin(LOGIN);
        List<Client> expectedList = Arrays.asList(expectedClient1, expectedClient2);

        when(connection.prepareStatement(SELECT_ALL_CLIENTS)).thenReturn(preparedStatement);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(ID);
        when(resultSet.getString("login")).thenReturn(LOGIN);
        when(resultSet.getString("name")).thenReturn(NAME);
        when(resultSet.getString("surname")).thenReturn(SURNAME);
        when(resultSet.getString("itn")).thenReturn(ITN);

        List<Client> resultList = clientRepository.getAllClients(1);
        assertNotNull(resultList);
        assertEquals(expectedList, resultList);
        assertEquals(expectedList.size(), resultList.size());

        verify(connection).prepareStatement(SELECT_ALL_CLIENTS);
        verify(preparedStatement).setInt(1, 1);
        verify(resultSet, times(3)).next();
    }

    @Test
    public void getAllClientsWhenNotFoundClients() throws SQLException {
        List<Client> expectedList = Collections.emptyList();

        when(connection.prepareStatement(SELECT_ALL_CLIENTS)).thenReturn(preparedStatement);
        when(resultSet.next()).thenReturn(false);

        List<Client> resultList = clientRepository.getAllClients(1);
        assertNotNull(resultList);
        assertEquals(expectedList, resultList);
        assertTrue(resultList.isEmpty());

        verify(connection).prepareStatement(SELECT_ALL_CLIENTS);
        verify(preparedStatement).setInt(1, 1);
        verify(resultSet).next();
    }

    @Test
    public void getCountOfPageWhenFoundClients() throws SQLException {
        double expected = 11.0;
        when(statement.executeQuery(SELECT_COUNT)).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getDouble(1)).thenReturn(expected);

        double result = clientRepository.getCountOfPage();
        assertEquals(expected, result, 0.0);
    }

    @Test
    public void getCountOfPageWhenNotFoundClients() throws SQLException {
        double expected = 0.0;

        when(statement.executeQuery(SELECT_COUNT)).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        double result = clientRepository.getCountOfPage();
        assertEquals(expected, result, 0.0);
    }

    @Test
    public void insertClientCommit() throws SQLException {
        ClientRegistrationDto clientRegistrationDto = new ClientRegistrationDto();
        clientRegistrationDto.setLogin(LOGIN);
        clientRegistrationDto.setPassword(PASSWORD);
        clientRegistrationDto.setName(NAME);
        clientRegistrationDto.setSurname(SURNAME);
        clientRegistrationDto.setItn(ITN);

        Client expectedClient = new Client(NAME, SURNAME, ITN);
        expectedClient.setId(ID);
        expectedClient.setLogin(LOGIN);
        expectedClient.setPassword(PASSWORD);

        when(connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(1)).thenReturn(ID);
        when(connection.prepareStatement(INSERT_CLIENT)).thenReturn(preparedStatement1);

        Optional<Client> resultClient = clientRepository.insertClient(clientRegistrationDto);
        assertNotNull(resultClient);
        assertTrue(resultClient.isPresent());
        assertEquals(expectedClient, resultClient.get());

        verify(connection).setAutoCommit(false);
        verify(preparedStatement).setString(1, LOGIN);
        verify(preparedStatement).setString(2, PASSWORD);
        verify(preparedStatement).setString(3, UserRole.CLIENT.toString());
        verify(preparedStatement).execute();
        verify(preparedStatement).getGeneratedKeys();
        verify(resultSet).next();
        verify(preparedStatement1).setLong(1, ID);
        verify(preparedStatement1).setString(2, NAME);
        verify(preparedStatement1).setString(3, SURNAME);
        verify(preparedStatement1).setString(4, ITN);
        verify(preparedStatement1).execute();
        verify(connection).commit();
        verify(preparedStatement).close();
        verify(connection).close();
        verify(resultSet).close();
    }

    @Test(expected = ClientException.class)
    public void insertClientRollback() throws SQLException {
        ClientRegistrationDto clientRegistrationDto = new ClientRegistrationDto();
        clientRegistrationDto.setLogin(LOGIN);
        clientRegistrationDto.setPassword(PASSWORD);
        clientRegistrationDto.setName(NAME);
        clientRegistrationDto.setSurname(SURNAME);
        clientRegistrationDto.setItn(ITN);

        when(connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
        when(preparedStatement.execute()).thenThrow(SQLException.class);

        clientRepository.insertClient(clientRegistrationDto);

        verify(connection).setAutoCommit(false);
        verify(preparedStatement).setString(1, LOGIN);
        verify(preparedStatement).setString(2, PASSWORD);
        verify(preparedStatement).setString(3, UserRole.CLIENT.toString());
        verify(preparedStatement).execute();
        verify(connection).rollback();
        verify(preparedStatement).close();
        verify(connection).close();
    }

    @Test
    public void deleteClientByIdCommit() throws SQLException {

        when(connection.prepareStatement(DELETE_REPORTS_BY_CLIENT_ID)).thenReturn(preparedStatement);
        when(connection.prepareStatement(DELETE_CLIENT_BY_ID)).thenReturn(preparedStatement1);
        when(connection.prepareStatement(DELETE_USER_BY_ID)).thenReturn(preparedStatement2);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement1.executeUpdate()).thenReturn(1);
        when(preparedStatement2.executeUpdate()).thenReturn(1);

        boolean result = clientRepository.deleteClientById(ID);
        assertTrue(result);

        verify(connection).setAutoCommit(false);
        verify(preparedStatement).setLong(1, ID);
        verify(preparedStatement1).setLong(1, ID);
        verify(preparedStatement2).setLong(1, ID);
        verify(connection).commit();
        verify(preparedStatement).close();
        verify(connection).close();

    }

    @Test(expected = ClientException.class)
    public void deleteClientByIdRollback() throws SQLException {

        when(connection.prepareStatement(DELETE_REPORTS_BY_CLIENT_ID)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenThrow(SQLException.class);

        clientRepository.deleteClientById(ID);

        verify(connection).setAutoCommit(false);
        verify(preparedStatement).executeUpdate();
        verify(connection).rollback();
        verify(preparedStatement).close();
        verify(connection).close();
    }

    @Test
    public void getClientsByParameterWhenFoundClients() throws SQLException {
        Client expectedClient1 = new Client(NAME, SURNAME, ITN);
        expectedClient1.setId(ID);
        expectedClient1.setLogin(LOGIN);
        Client expectedClient2 = new Client(NAME, SURNAME, ITN);
        expectedClient2.setId(ID);
        expectedClient2.setLogin(LOGIN);
        List<Client> expectedList = Arrays.asList(expectedClient1, expectedClient2);
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("name", NAME);
        parameters.put("surname", SURNAME);
        String sql = "select * from client join user on user.id=client.id where name = '"
                     + parameters.get("name") + "' and surname = '" + parameters.get("surname") + "';";


        when(statement.executeQuery(sql)).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(ID);
        when(resultSet.getString("login")).thenReturn(LOGIN);
        when(resultSet.getString("name")).thenReturn(NAME);
        when(resultSet.getString("surname")).thenReturn(SURNAME);
        when(resultSet.getString("itn")).thenReturn(ITN);

        List<Client> resultList = clientRepository.getClientsByParameter(parameters);
        assertNotNull(resultList);
        assertEquals(expectedList, resultList);
        assertEquals(expectedList.size(), resultList.size());

        verify(connection).createStatement();
        verify(statement).executeQuery(sql);
        verify(resultSet, times(3)).next();
    }

    @Test
    public void getClientsByParameterWhenNotFoundClients() throws SQLException {
        List<Client> expectedList = Collections.emptyList();
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("name", NAME);
        parameters.put("surname", SURNAME);
        String sql = "select * from client join user on user.id=client.id where name = '"
                + parameters.get("name") + "' and surname = '" + parameters.get("surname") + "';";

        when(statement.executeQuery(sql)).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        List<Client> resultList = clientRepository.getClientsByParameter(parameters);
        assertNotNull(resultList);
        assertEquals(expectedList, resultList);
        assertTrue(resultList.isEmpty());

        verify(connection).createStatement();
        verify(statement).executeQuery(sql);
        verify(resultSet).next();
    }

}
