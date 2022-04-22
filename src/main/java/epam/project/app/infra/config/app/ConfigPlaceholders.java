package epam.project.app.infra.config.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import epam.project.app.infra.web.Placeholder;
import epam.project.app.infra.web.QueryParameterResolver;
import epam.project.app.logic.builder.Building;
import epam.project.app.logic.controller.*;
import epam.project.app.logic.parser.Parsing;
import epam.project.app.logic.validator.JsonValidator;
import epam.project.app.logic.validator.Validating;
import epam.project.app.logic.validator.XmlValidator;
import epam.project.app.logic.builder.JsonBuilder;
import epam.project.app.logic.parser.JsonParser;
import epam.project.app.logic.builder.XmlBuilder;
import epam.project.app.logic.parser.XmlParser;
import epam.project.app.logic.repository.ClientRepository;
import epam.project.app.logic.repository.ReportRepository;
import epam.project.app.logic.repository.UserRepository;
import epam.project.app.logic.service.ClientService;
import epam.project.app.logic.service.ReportService;
import epam.project.app.logic.service.UserService;
import lombok.extern.log4j.Log4j2;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j2
public class ConfigPlaceholders {
    public List<Placeholder> config(DataSource dataSource, ObjectMapper objectMapper) {
        log.info("start configure placeholders");
        List<Placeholder> placeholders = new ArrayList<>();
        QueryParameterResolver queryParameterResolver = new QueryParameterResolver(objectMapper);

        UserController userController = createUserController(dataSource, queryParameterResolver);
        ClientController clientController = createClientController(dataSource, queryParameterResolver);
        ReportService reportService = createReportService(dataSource, objectMapper);
        ReportController reportController = new ReportController(reportService, queryParameterResolver);
        ReportUploadController reportUploadController = new ReportUploadController(reportService);
        ReportEditController reportEditController = new ReportEditController(reportService, queryParameterResolver);

        placeholders.add(new Placeholder("POST", "login", userController::login));
        placeholders.add(new Placeholder("POST", "logout", userController::logout));
        placeholders.add(new Placeholder("POST", "changeLocale", userController::changeLocale));
        placeholders.add(new Placeholder("POST", "registration", clientController::registration));
        placeholders.add(new Placeholder("POST", "upload", reportUploadController::uploadFile));
        placeholders.add(new Placeholder("POST", "updateStatusOfReport", reportController::updateStatusOfReport));
        placeholders.add(new Placeholder("POST", "deleteReportById", reportController::deleteReportById));
        placeholders.add(new Placeholder("POST", "deleteClientById", clientController::deleteClientById));
        placeholders.add(new Placeholder("POST", "saveReportChanges", reportEditController::saveReportChanges));
        placeholders.add(new Placeholder("GET", "toHome", userController::toHome));
        placeholders.add(new Placeholder("GET", "allReportsByClient", reportController::getAllReportsByClientId));
        placeholders.add(new Placeholder("GET", "searchClients", clientController::searchClientsByParameters));
        placeholders.add(new Placeholder("GET", "allClients", clientController::getAllClients));
        placeholders.add(new Placeholder("GET", "allReports", reportController::getAllReports));
        placeholders.add(new Placeholder("GET", "filterClientReports", reportController::getClientReportsByFilterParameters));
        placeholders.add(new Placeholder("GET", "filterAllReports", reportController::getAllReportsByFilterParameters));
        placeholders.add(new Placeholder("GET", "showReport", reportController::showReport));
        placeholders.add(new Placeholder("GET", "editReport", reportEditController::editReport));
        return placeholders;
    }

    private ReportService createReportService(DataSource dataSource, ObjectMapper objectMapper) {

        Map<String,Validating> validators = Map.of(".xml",new XmlValidator(),".json",new JsonValidator(objectMapper));
        Map<String,Parsing> parsers = Map.of(".xml",new XmlParser(),".json",new JsonParser(objectMapper));
        Map<String,Building> builders = Map.of(".xml",new XmlBuilder(),".json",new JsonBuilder(objectMapper));

        return new ReportService(new ReportRepository(dataSource), validators, parsers, builders);
    }

    private ClientController createClientController(DataSource dataSource, QueryParameterResolver queryParameterResolver) {
        return new ClientController(new ClientService(new ClientRepository(dataSource)), queryParameterResolver);
    }

    private UserController createUserController(DataSource dataSource, QueryParameterResolver queryParameterResolver) {
        return new UserController(new UserService(new UserRepository(dataSource)), queryParameterResolver);
    }
}
