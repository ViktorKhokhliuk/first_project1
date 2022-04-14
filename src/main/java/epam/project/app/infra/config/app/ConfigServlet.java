package epam.project.app.infra.config.app;

import epam.project.app.infra.web.BaseServlet;
import epam.project.app.infra.web.DispatcherRequest;
import epam.project.app.infra.web.Placeholder;
import epam.project.app.infra.web.ProcessModelAndView;
import epam.project.app.infra.web.exception.ExceptionHandler;
import jakarta.servlet.http.HttpServlet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
public class ConfigServlet {
    @Getter
    private final Map<String, HttpServlet> servlets;

    public void configBaseServlet(PropertyLoader properties, List<Placeholder> placeholders) {
        log.info("start configure servlet");
        String servletName = properties.getConfigs().get("servlet_name");
        String urlPattern = properties.getConfigs().get("servlet_url_pattern");

        BaseServlet baseServlet = new BaseServlet(servletName, new DispatcherRequest(placeholders),
                new ProcessModelAndView(), new ExceptionHandler());

        servlets.put(urlPattern, baseServlet);
    }

}
