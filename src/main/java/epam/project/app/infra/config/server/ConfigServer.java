package epam.project.app.infra.config.server;

import epam.project.app.infra.config.app.PropertyLoader;
import epam.project.app.infra.web.Server;
import jakarta.servlet.http.HttpServlet;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.util.Map;

public class ConfigServer {

    public Server createServer(PropertyLoader propertyLoader, Map<String, HttpServlet> servlets) {
        String port = propertyLoader.getConfigs().get("tomcat_port");
        String baseDir = propertyLoader.getConfigs().get("base_dir");
        String contextPath = propertyLoader.getConfigs().get("context_path");

        Server server = new Server();
        Tomcat tomcat = server.getTomcat();
        tomcat.setBaseDir(baseDir);
        tomcat.setPort(Integer.parseInt(port));

        String docBase = new File("webapp").getAbsolutePath();

        Context context = tomcat.addWebapp(contextPath, docBase);

        servlets.entrySet().stream()
                .iterator()
                .forEachRemaining(entry -> registerServlet(context, entry));

        return server;
    }

    private void registerServlet(Context context, Map.Entry<String, HttpServlet> entry) {
        String urlPattern = entry.getKey();
        HttpServlet servlet = entry.getValue();
        String servletName = entry.getValue().getServletName();

        Tomcat.addServlet(context, servletName, servlet);
        context.addServletMappingDecoded(urlPattern, servletName);
    }
}
