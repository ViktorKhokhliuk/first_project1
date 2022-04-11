package epam.project.app.logic.controller;

import epam.project.app.infra.web.ModelAndView;
import epam.project.app.infra.web.QueryParameterResolver;
import epam.project.app.logic.entity.dto.ClientRegistrationDto;
import epam.project.app.logic.entity.user.Client;
import epam.project.app.logic.service.ClientService;
import jakarta.servlet.http.HttpServletRequest;
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
public class ClientControllerTest {

    @Mock
    private ClientService clientService;
    @Mock
    private QueryParameterResolver parameterResolver;
    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private ClientController clientController;

    private static final Long ID = 0L;
    private static final String LOGIN = "tony";
    private static final String PASSWORD = "tony123";

    @Test
    public void registration() {
        ClientRegistrationDto registrationDto = new ClientRegistrationDto();
        registrationDto.setLogin(LOGIN);
        registrationDto.setPassword(PASSWORD);
        Client expectedClient = new Client();

        when(parameterResolver.getObject(request, ClientRegistrationDto.class)).thenReturn(registrationDto);
        when(clientService.registration(registrationDto)).thenReturn(expectedClient);

        ModelAndView modelAndView = clientController.registration(request);
        assertNotNull(modelAndView);
        assertEquals("/index.jsp",modelAndView.getView());
        assertTrue(modelAndView.isRedirect());

        verify(clientService).registration(registrationDto);
    }

    @Test
    public void getAllClients() {
        double countOfPage = 1.0;
        int page = 1;
        List<Client> expectedClients = Arrays.asList(new Client(),new Client());

        when(request.getParameter("page")).thenReturn("1");
        when(clientService.getAllClients(anyInt())).thenReturn(expectedClients);
        when(clientService.getCountOfPage()).thenReturn(countOfPage);

        ModelAndView modelAndView = clientController.getAllClients(request);
        assertNotNull(modelAndView);
        assertEquals(expectedClients,modelAndView.getAttributes().get("clients"));
        assertEquals(page,modelAndView.getAttributes().get("page"));
        assertEquals(countOfPage,modelAndView.getAttributes().get("countOfPage"));
        assertEquals("/inspector/allClients.jsp",modelAndView.getView());

        verify(clientService).getAllClients(anyInt());
        verify(clientService).getCountOfPage();
    }

    @Test
    public void deleteClientById() {
        int page = 1;

        when(request.getParameter("page")).thenReturn(String.valueOf(page));
        when(request.getParameter("clientId")).thenReturn(ID.toString());

        ModelAndView modelAndView = clientController.deleteClientById(request);
        assertEquals("/service/allClients?page="+page,modelAndView.getView());
        assertTrue(modelAndView.isRedirect());

        verify(clientService).deleteClientById(ID);
    }

    @Test
    public void searchClientsByParametersWhenNoParameters() {
        double countOfPage = 1.0;
        int page = 1;
        List<Client> expectedClients = Arrays.asList(new Client(),new Client());
        Enumeration<String> parameterNames = new Vector<String>().elements();

        when(request.getParameterNames()).thenReturn(parameterNames);
        when(request.getParameter("page")).thenReturn("1");
        when(clientService.getAllClients(anyInt())).thenReturn(expectedClients);
        when(clientService.getCountOfPage()).thenReturn(countOfPage);

        ModelAndView modelAndView = clientController.searchClientsByParameters(request);
        assertNotNull(modelAndView);
        assertEquals(expectedClients,modelAndView.getAttributes().get("clients"));
        assertEquals(page,modelAndView.getAttributes().get("page"));
        assertEquals(countOfPage,modelAndView.getAttributes().get("countOfPage"));
        assertEquals("/inspector/allClients.jsp",modelAndView.getView());

        verify(clientService).getAllClients(anyInt());
        verify(clientService).getCountOfPage();
        verify(request).getParameterNames();
    }

    @Test
    public void searchClientsByParameters() {
        Client client = new Client("Tony","Smith","123456789");
        client.setId(ID);
        client.setLogin(LOGIN);
        client.setPassword(PASSWORD);
        List<Client> expectedClients = List.of(client);

        Vector<String> names = new Vector<>();
        names.add("name");
        names.add("surname");

        Enumeration<String> parameterNames = names.elements();

        Map<String,String> parameters = new HashMap<>();
        parameters.put(names.get(0),client.getName());
        parameters.put(names.get(1),client.getSurname());

        when(request.getParameterNames()).thenReturn(parameterNames);
        when(request.getParameter("name")).thenReturn(client.getName());
        when(request.getParameter("surname")).thenReturn(client.getSurname());
        when(clientService.searchClientsByParameters(parameters)).thenReturn(expectedClients);

        ModelAndView modelAndView = clientController.searchClientsByParameters(request);
        assertNotNull(modelAndView);
        assertEquals(expectedClients,modelAndView.getAttributes().get("clients"));
        assertEquals("/inspector/allClients.jsp",modelAndView.getView());

        verify(clientService).searchClientsByParameters(parameters);
        verify(request).getParameterNames();
    }

}
