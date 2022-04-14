package epam.project.app.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import epam.project.app.infra.web.QueryParameterResolver;
import epam.project.app.logic.entity.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QueryParameterResolverTest {

    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private HttpServletRequest request;
    @Mock
    private Enumeration<String> parameterNames;

    @InjectMocks
    private QueryParameterResolver queryParameterResolver;

    private UserDTO userDTO;
    private final Map<String, String> parameters = new HashMap<>();

    private static final String LOGIN = "tony";
    private static final String PASSWORD = "tony123";

    @Before
    public void init() {
        userDTO = new UserDTO();
        userDTO.setLogin(LOGIN);
        userDTO.setPassword(PASSWORD);

        parameters.put("login", LOGIN);
        parameters.put("password", PASSWORD);

        when(request.getParameterNames()).thenReturn(parameterNames);
        when(parameterNames.hasMoreElements()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(parameterNames.nextElement()).thenReturn("login").thenReturn("password");
        when(request.getParameter("login")).thenReturn(LOGIN);
        when(request.getParameter("password")).thenReturn(PASSWORD);
        when(objectMapper.convertValue(parameters, UserDTO.class)).thenReturn(userDTO);
    }

    @Test
    public void getObject() {
        UserDTO result = queryParameterResolver.getObject(request, UserDTO.class);
        assertEquals(userDTO, result);
    }
}
