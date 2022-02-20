package epam.project.app.infra.config.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import epam.project.app.infra.web.Placeholder;
import epam.project.app.infra.web.QueryParameterResolver;
import epam.project.app.logic.controller.*;
import epam.project.app.logic.parse.JsonBuilder;
import epam.project.app.logic.parse.JsonParser;
import epam.project.app.logic.parse.XmlBuilder;
import epam.project.app.logic.parse.XmlParser;
import epam.project.app.logic.repository.ClientRepository;
import epam.project.app.logic.repository.ReportRepository;
import epam.project.app.logic.repository.UserRepository;
import epam.project.app.logic.service.ClientService;
import epam.project.app.logic.service.ReportService;
import epam.project.app.logic.service.UserService;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class ConfigPlaceholders {
    public List<Placeholder> config(DataSource dataSource, ObjectMapper objectMapper) {
        List<Placeholder> placeholders = new ArrayList<>();
        QueryParameterResolver queryParameterResolver = new QueryParameterResolver(objectMapper);

        UserController userController = createUserController(dataSource, queryParameterResolver);
        ClientController clientController = createClientController(dataSource, queryParameterResolver);
        ReportService reportService = createReportService(dataSource, objectMapper);
        ReportController reportController = new ReportController(reportService, queryParameterResolver);
        ReportUploaderController reportUploaderController = new ReportUploaderController(reportService);
        ReportEditController reportEditController = new ReportEditController(reportService, queryParameterResolver);

        placeholders.add(new Placeholder("POST", "login", userController::login));
        placeholders.add(new Placeholder("POST", "logout", userController::logout));
        placeholders.add(new Placeholder("POST", "registration", clientController::registration));
        placeholders.add(new Placeholder("POST", "upload", reportUploaderController::uploadFile));
        placeholders.add(new Placeholder("POST", "updateStatusOfReport", reportController::updateStatusOfReport));
        placeholders.add(new Placeholder("POST", "deleteReportById", reportController::deleteReportById));
        placeholders.add(new Placeholder("POST", "deleteClientById", clientController::deleteClientById));
        placeholders.add(new Placeholder("POST", "saveReportChanges", reportEditController::saveReportChanges));
        placeholders.add(new Placeholder("GET", "toHome", userController::toHome));
        placeholders.add(new Placeholder("GET", "getAllReportsByClientId", reportController::getAllReportsByClientId));
        placeholders.add(new Placeholder("GET", "searchClients", clientController::searchClientsByParameters));
        placeholders.add(new Placeholder("GET", "getAllClients", clientController::getAllClients));
        placeholders.add(new Placeholder("GET", "getAllReports", reportController::getAllReports));
        placeholders.add(new Placeholder("GET", "filterClientReports", reportController::getClientReportsByFilterParameters));
        placeholders.add(new Placeholder("GET", "filterAllReports", reportController::getAllReportsByFilterParameters));
        placeholders.add(new Placeholder("GET", "showReport", reportController::showReport));
        placeholders.add(new Placeholder("GET", "editReport", reportEditController::editReport));
        return placeholders;
    }

    private ReportService createReportService(DataSource dataSource, ObjectMapper objectMapper) {
        return new ReportService(new ReportRepository(dataSource), new XmlParser(), new XmlBuilder(), new JsonParser(objectMapper), new JsonBuilder(objectMapper));
    }

    private ClientController createClientController(DataSource dataSource, QueryParameterResolver queryParameterResolver) {
        return new ClientController(new ClientService(new ClientRepository(dataSource)), queryParameterResolver);
    }

    private UserController createUserController(DataSource dataSource, QueryParameterResolver queryParameterResolver) {
        return new UserController(new UserService(new UserRepository(dataSource)), queryParameterResolver);
    }
}
