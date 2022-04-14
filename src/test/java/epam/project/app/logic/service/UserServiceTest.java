package epam.project.app.logic.service;

import epam.project.app.logic.entity.dto.UserDTO;

import static org.junit.Assert.*;

import epam.project.app.logic.entity.user.Client;
import epam.project.app.logic.entity.user.Inspector;
import epam.project.app.logic.entity.user.User;
import epam.project.app.logic.exception.UserException;
import epam.project.app.logic.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private static final String LOGIN = "tony";
    private static final String PASSWORD = "tony123";
    private static final UserDTO userDTO = new UserDTO(LOGIN, PASSWORD);

    @Test
    public void getUserByLoginReturnClient() {
        User client = new Client();
        client.setLogin(LOGIN);
        client.setPassword(PASSWORD);

        when(userRepository.getUserByLogin(userDTO.getLogin())).thenReturn(Optional.of(client));
        User resultUser = userService.getUserByLogin(userDTO);
        assertEquals(client, resultUser);
    }

    @Test
    public void getUserByLoginReturnInspector() {
        User inspector = new Inspector();
        inspector.setLogin(LOGIN);
        inspector.setPassword(PASSWORD);

        when(userRepository.getUserByLogin(userDTO.getLogin())).thenReturn(Optional.of(inspector));
        User resultUser = userService.getUserByLogin(userDTO);
        assertEquals(inspector, resultUser);
    }

    @Test(expected = UserException.class)
    public void getUserByLoginThrowExceptionWhenNotFoundUser() {
        when(userRepository.getUserByLogin(userDTO.getLogin())).thenReturn(Optional.empty());
        userService.getUserByLogin(userDTO);
    }

    @Test(expected = UserException.class)
    public void getUserByLoginThrowExceptionWhenPasswordNotEquals() {
        User someUser = new Client();
        someUser.setLogin(LOGIN);
        someUser.setPassword("password");
        when(userRepository.getUserByLogin(userDTO.getLogin())).thenReturn(Optional.of(someUser));
        userService.getUserByLogin(userDTO);
    }
}
