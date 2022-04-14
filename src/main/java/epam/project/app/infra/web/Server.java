package epam.project.app.infra.web;


import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpSessionListener;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;


import java.util.List;

@Log4j2
public class Server {
    @Getter
    private final Tomcat tomcat;
    @Setter
    private Context context;

    public Server() {
        tomcat = new Tomcat();
    }

    public void addFilter(FilterDef filterDef, FilterMap filterMap) {
        context.addFilterDef(filterDef);
        log.info("Add filter --> " + filterDef.getFilterName());
        context.addFilterMap(filterMap);
        log.info("Add filter --> " + filterMap.getFilterName());
    }

    public void addServlet(String servletName, String urlPattern, HttpServlet servlet) {
        Tomcat.addServlet(context, servletName, servlet);
        log.info("Add servlet: servlet name --> " + servletName);
        context.addServletMappingDecoded(urlPattern, servletName);
        log.info("Add servlet mapping: url --> " + urlPattern);
    }

    @SneakyThrows
    public void start() {
        tomcat.getServer();
        tomcat.getConnector();
        tomcat.getServer().start();
        tomcat.getServer().await();
    }

    public void addListener(List<HttpSessionListener> localeSessionListener) {
        context.setApplicationLifecycleListeners(localeSessionListener.toArray());
        log.info("Set session listeners ");
    }
}
