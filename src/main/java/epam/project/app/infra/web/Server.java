package epam.project.app.infra.web;

import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpSessionListener;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;

import java.io.File;
import java.util.List;

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
        context.addFilterMap(filterMap);
    }

    public void addServlet(String servletName, String urlPattern, HttpServlet servlet) {
        Tomcat.addServlet(context, servletName, servlet);
        context.addServletMappingDecoded(urlPattern, servletName);
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
    }
}
