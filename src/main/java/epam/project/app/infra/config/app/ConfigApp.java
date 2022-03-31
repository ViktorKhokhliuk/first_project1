package epam.project.app.infra.config.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import epam.project.app.Application;
import epam.project.app.infra.web.Placeholder;
import epam.project.app.infra.web.SecurityFilter;
import epam.project.app.infra.web.Server;
import epam.project.app.infra.config.db.ConfigDataSource;
import epam.project.app.infra.config.db.ConfigLiquibase;
import epam.project.app.infra.config.server.ConfigServer;
import epam.project.app.infra.web.encoding.EncodingFilter;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServlet;
import lombok.extern.log4j.Log4j2;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class ConfigApp {
    public Application config() {
        log.info("start config");
        PropertyLoader propertyLoader = new PropertyLoader();
        propertyLoader.loadConfig("app.yaml");

        ConfigDataSource configDataSource = new ConfigDataSource();
        DataSource dataSource = configDataSource.createDataSource(propertyLoader);
        log.info("set connection to db");

        ConfigLiquibase configLiquibase = new ConfigLiquibase(dataSource);
        configLiquibase.updateDatabase(propertyLoader);
        log.info("update database");

        ConfigPlaceholders configPlaceholders = new ConfigPlaceholders();
        ObjectMapper objectMapper = new ObjectMapper();
        List<Placeholder> placeholders = configPlaceholders.config(dataSource, objectMapper);

        ConfigServlet configServlet = new ConfigServlet(new HashMap<>());
        configServlet.configBaseServlet(propertyLoader, placeholders);

        ConfigServer configServer = new ConfigServer();
        Map<String, HttpServlet> servlets = configServlet.getServlets();
        List<Filter> filters = List.of(new SecurityFilter(),new EncodingFilter());

        Server server = configServer.createServer(propertyLoader, servlets, filters);

        return new Application(server);
    }


}
