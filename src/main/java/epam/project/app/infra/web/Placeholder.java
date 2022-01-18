package epam.project.app.infra.web;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Value;

import java.util.function.Function;

@Value
public class Placeholder {
    String method;
    String url;
    Function<HttpServletRequest, ModelAndView> process;
}
