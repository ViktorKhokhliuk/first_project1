package epam.project.app.infra.config.app;

import epam.project.app.infra.web.BaseServlet;
import epam.project.app.infra.web.DispatcherRequest;
import epam.project.app.infra.web.Placeholder;
import epam.project.app.infra.web.ProcessModelAndView;
import epam.project.app.infra.web.exception.ExceptionHandler;
import jakarta.servlet.http.HttpServlet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ConfigServlet {
    @Getter
    private final Map<String, HttpServlet> servlets;

    public void configBaseServlet(PropertyLoader properties, List<Placeholder> placeholders) {
        String servletName = properties.getConfigs().get("servlet_name");
        String urlPattern = properties.getConfigs().get("servlet_url_pattern");

        BaseServlet baseServlet = new BaseServlet(servletName, new DispatcherRequest(placeholders),
                new ProcessModelAndView(), new ExceptionHandler());

        servlets.put(urlPattern, baseServlet);
    }

}
