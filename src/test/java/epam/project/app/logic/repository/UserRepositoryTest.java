package epam.project.app.logic.repository;

import epam.project.app.logic.entity.dto.UserDTO;
import epam.project.app.logic.entity.user.Client;
import epam.project.app.logic.entity.user.Inspector;
import epam.project.app.logic.entity.user.User;
import epam.project.app.logic.entity.user.UserRole;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryTest {

    @Mock
    private DataSource dataSource;
    @Mock
    private Connection connection;
    @Mock
    private ResultSet resultSet;
    @Mock
    private PreparedStatement preparedStatement;

    @InjectMocks
    private UserRepository userRepository;

    private static final String LOGIN = "tony";
    private static final String PASSWORD = "tony123";
    private static final String NAME = "Tony";
    private static final String SURNAME = "Tony";
    private static final String ITN = "123456789";
    private static final String SELECT_USER_BY_LOGIN = "select * from user left join client on user.id=client.id where user.login= ?;";
    private static final Long ID = 1L;
    private static final UserDTO userDTO = new UserDTO(LOGIN, PASSWORD);

    @Before
    public void init() throws SQLException {
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(SELECT_USER_BY_LOGIN)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.getLong("id")).thenReturn(ID);
        when(resultSet.getString("password")).thenReturn(PASSWORD);
    }

    @Test
    public void getUserByLoginReturnClient() throws SQLException {
        User expectedClient = new Client(NAME, SURNAME, ITN);
        expectedClient.setId(ID);
        expectedClient.setLogin(LOGIN);
        expectedClient.setPassword(PASSWORD);
        expectedClient.setUserRole(UserRole.CLIENT);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("role")).thenReturn("CLIENT");
        when(resultSet.getString("name")).thenReturn(NAME);
        when(resultSet.getString("surname")).thenReturn(SURNAME);
        when(resultSet.getString("itn")).thenReturn(ITN);

        Optional<User> resultClient = userRepository.getUserByLogin(userDTO.getLogin());
        assertNotNull(resultClient);
        assertTrue(resultClient.isPresent());
        assertEquals(expectedClient, resultClient.get());

        verify(preparedStatement).setString(1, LOGIN);
        verify(connection).prepareStatement(SELECT_USER_BY_LOGIN);
    }

    @Test
    public void getUserByLoginReturnInspector() throws SQLException {
        User expectedInspector = new Inspector();
        expectedInspector.setId(ID);
        expectedInspector.setLogin(LOGIN);
        expectedInspector.setPassword(PASSWORD);
        expectedInspector.setUserRole(UserRole.INSPECTOR);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("role")).thenReturn("INSPECTOR");

        Optional<User> resultInspector = userRepository.getUserByLogin(userDTO.getLogin());
        assertNotNull(resultInspector);
        assertTrue(resultInspector.isPresent());
        assertEquals(expectedInspector, resultInspector.get());

        verify(preparedStatement).setString(1, LOGIN);
        verify(connection).prepareStatement(SELECT_USER_BY_LOGIN);
    }

    @Test
    public void getUserByLoginWhenNotFoundUser() throws SQLException {
        when(resultSet.next()).thenReturn(false);

        Optional<User> resultUser = userRepository.getUserByLogin(userDTO.getLogin());
        assertNotNull(resultUser);
        assertFalse(resultUser.isPresent());

        verify(preparedStatement).setString(1, LOGIN);
        verify(connection).prepareStatement(SELECT_USER_BY_LOGIN);
    }
}
