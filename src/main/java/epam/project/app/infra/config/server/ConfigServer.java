package epam.project.app.infra.config.server;

import epam.project.app.infra.config.app.PropertyLoader;
import epam.project.app.infra.web.LocaleSessionListener;
import epam.project.app.infra.web.Server;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServlet;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Map;
@Log4j2
public class ConfigServer {

    public Server createServer(PropertyLoader propertyLoader, Map<String, HttpServlet> servlets, List<Filter> filters) {
        log.info("start creating server");
        String port = propertyLoader.getConfigs().get("tomcat_port");
        String baseDir = propertyLoader.getConfigs().get("base_dir");
        String contextPath = propertyLoader.getConfigs().get("context_path");

        Server server = new Server();
        Tomcat tomcat = server.getTomcat();
        tomcat.setBaseDir(baseDir);
        tomcat.setPort(Integer.parseInt(port));

        String docBase = new File("webapp").getAbsolutePath();

        Context context = tomcat.addWebapp(contextPath, docBase);
        server.setContext(context);

        filters.stream().iterator().forEachRemaining(filter -> addFilter(server, filter));
        servlets.entrySet().stream()
                .iterator()
                .forEachRemaining(entry -> registerServlet(server, entry));
        registerSessionListeners(server);
        context.setAllowCasualMultipartParsing(true);
        return server;
    }

    private void registerSessionListeners(Server server) {
        log.info("start register session listener");
        Locale selectedLocale = new Locale("en");
        LocaleSessionListener localeSessionListener = new LocaleSessionListener(List.of(new Locale("en"), new Locale("ru")),selectedLocale);
        server.addListener(List.of(localeSessionListener));
    }

    private void registerServlet(Server server, Map.Entry<String, HttpServlet> entry) {
        log.info("start register servlet");
        String urlPattern = entry.getKey();
        HttpServlet servlet = entry.getValue();
        String servletName = entry.getValue().getServletName();

        server.addServlet(servletName, urlPattern, servlet);
    }

    private void addFilter(Server server, Filter filter) {
        log.info("Start configure encoding filter and security");
        FilterDef filterDef = new FilterDef();
        filterDef.setFilterName(filter.getClass().getSimpleName());
        filterDef.setFilterClass(filter.getClass().getName());

        FilterMap filterMap = new FilterMap();
        filterMap.setFilterName(filter.getClass().getSimpleName());
        filterMap.addURLPattern("/*");

        server.addFilter(filterDef, filterMap);
    }
}
