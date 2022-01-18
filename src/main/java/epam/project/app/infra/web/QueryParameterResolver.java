package epam.project.app.infra.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class QueryParameterResolver {
    private final ObjectMapper objectMapper;

    public <T> T getObject(HttpServletRequest req, Class<T> aClass) {
        Map<String, String> parameters = new HashMap<>();
        Enumeration<String> parameterNames = req.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            String parameter = req.getParameter(parameterName);
            parameters.put(parameterName, parameter);
        }
        return objectMapper.convertValue(parameters, aClass);
    }
}
