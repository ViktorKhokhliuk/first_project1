package epam.project.app.logic.service;

import epam.project.app.logic.entity.dto.ClientRegistrationDto;
import epam.project.app.logic.entity.user.Client;
import epam.project.app.logic.entity.user.UserRole;
import epam.project.app.logic.exception.ClientException;
import epam.project.app.logic.repository.ClientRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    private static final Long ID = 1L;
    private static final String LOGIN = "tony";
    private static final String PASSWORD = "tony123";
    private static final String NAME = "Tony";
    private static final String SURNAME = "Smith";
    private static final String ITN = "123456789";

    @Test
    public void registration() {
        Client expectedClient = new Client();
        expectedClient.setId(ID);
        expectedClient.setLogin(LOGIN);
        expectedClient.setPassword(PASSWORD);

        ClientRegistrationDto clientRegistrationDto = new ClientRegistrationDto(LOGIN,PASSWORD,NAME,SURNAME,ITN, UserRole.CLIENT);

        when(clientRepository.insertClient(clientRegistrationDto)).thenReturn(Optional.of(expectedClient));
        Client resultClient = clientService.registration(clientRegistrationDto);

        assertNotNull(resultClient);
        assertEquals(expectedClient, resultClient);
        verify(clientRepository).insertClient(clientRegistrationDto);
    }

    @Test(expected = ClientException.class)
    public void registrationThrowExceptionWhenRepositoryReturnOptionalEmpty() {
        ClientRegistrationDto clientRegistrationDto = new ClientRegistrationDto(LOGIN,PASSWORD,NAME,SURNAME,ITN, UserRole.CLIENT);

        when(clientRepository.insertClient(clientRegistrationDto)).thenReturn(Optional.empty());
        clientService.registration(clientRegistrationDto);
    }

    @Test
    public void getCountOfPage() {
        double expected = 3.0;

        when(clientRepository.getCountOfPage()).thenReturn(11.0);

        double result = clientService.getCountOfPage();
        assertEquals(expected, result, 0.0);

        verify(clientRepository).getCountOfPage();
    }

    @Test
    public void getAllClients() {
        List<Client> expectedClients = Arrays.asList(new Client(),new Client());

        when(clientRepository.getAllClients(anyInt())).thenReturn(expectedClients);

        List<Client> resultClients = clientService.getAllClients(anyInt());
        assertNotNull(resultClients);
        assertEquals(expectedClients,resultClients);

        verify(clientRepository).getAllClients(anyInt());
    }

    @Test
    public void  deleteClientById() {
        when(clientRepository.deleteClientById(ID)).thenReturn(true);
        boolean result = clientService.deleteClientById(ID);

        assertTrue(result);

        verify(clientRepository).deleteClientById(ID);
    }

    @Test(expected = ClientException.class)
    public void deleteClientByIdThrowExceptionWhenRepositoryReturnFalse() {
        when(clientRepository.deleteClientById(ID)).thenReturn(false);
        clientService.deleteClientById(ID);
    }

    @Test
    public void searchClientsByParameters() {
        Map<String,String> parameters = Map.of("name", NAME, "surname", SURNAME);
        List<Client> expectedClients = Arrays.asList(new Client(),new Client());

        when(clientRepository.getClientsByParameter(parameters)).thenReturn(expectedClients);

        List<Client> resultClients = clientService.searchClientsByParameters(parameters);
        assertNotNull(resultClients);
        assertEquals(expectedClients,resultClients);

        verify(clientRepository).getClientsByParameter(parameters);
    }
}
