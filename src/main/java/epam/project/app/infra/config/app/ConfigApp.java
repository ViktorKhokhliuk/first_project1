package epam.project.app.infra.config.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import epam.project.app.Application;
import epam.project.app.infra.web.DispatcherRequest;
import epam.project.app.infra.web.Placeholder;
import epam.project.app.infra.web.Server;
import epam.project.app.infra.config.db.ConfigDataSource;
import epam.project.app.infra.config.db.ConfigLiquibase;
import epam.project.app.infra.config.server.ConfigServer;
import jakarta.servlet.http.HttpServlet;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigApp {
    public Application config() {
        //load property
        PropertyLoader propertyLoader = new PropertyLoader();
        propertyLoader.loadConfig("app.yaml");
        //set connection to db
        ConfigDataSource configDataSource = new ConfigDataSource();
        DataSource dataSource = configDataSource.createDataSource(propertyLoader);
        //update database
        ConfigLiquibase configLiquibase = new ConfigLiquibase(dataSource);
        configLiquibase.updateDatabase(propertyLoader);

        ConfigPlaceholders configPlaceholders = new ConfigPlaceholders();
        ObjectMapper objectMapper = new ObjectMapper();
        List<Placeholder> placeholders = configPlaceholders.config(dataSource, objectMapper);

        ConfigServlet configServlet = new ConfigServlet(new HashMap<>());
        ConfigServer configServer = new ConfigServer();

        configServlet.configBaseServlet(propertyLoader, placeholders);
        Server server = configServer.createServer(propertyLoader, configServlet.getServlets());

        return new Application(server);
    }


}
