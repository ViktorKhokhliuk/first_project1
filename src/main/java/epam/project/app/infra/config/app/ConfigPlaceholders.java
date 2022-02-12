package epam.project.app.infra.config.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import epam.project.app.infra.web.Placeholder;
import epam.project.app.infra.web.QueryParameterResolver;
import epam.project.app.logic.controller.ClientController;
import epam.project.app.logic.controller.ReportController;
import epam.project.app.logic.controller.ReportUploaderController;
import epam.project.app.logic.controller.UserController;
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
        ReportController reportController = createReportController(dataSource, queryParameterResolver);
        ReportUploaderController reportUploaderController = createReportUploaderController(dataSource);

        placeholders.add(new Placeholder("POST", "login", userController::login));
        placeholders.add(new Placeholder("POST", "logout", userController::logout));
        placeholders.add(new Placeholder("POST", "registration", clientController::registration));
        placeholders.add(new Placeholder("POST", "upload", reportUploaderController::uploadFile));
        placeholders.add(new Placeholder("POST", "updateStatusOfReport", reportController::updateStatusOfReport));
        placeholders.add(new Placeholder("POST", "deleteReportById", reportController::deleteReportById));
        placeholders.add(new Placeholder("POST", "deleteClientById", clientController::deleteClientById));
        placeholders.add(new Placeholder("GET", "getAllReportsByClientId", reportController::getAllReportsByClientId));
        placeholders.add(new Placeholder("GET", "getAllClients", clientController::getAllClients));
        placeholders.add(new Placeholder("GET", "filterReports", reportController::getReportsByFilterParameters));
        return placeholders;
    }

    private ReportUploaderController createReportUploaderController(DataSource dataSource) {
        return new ReportUploaderController(new ReportService(new ReportRepository(dataSource)));
    }

    private ReportController createReportController(DataSource dataSource, QueryParameterResolver queryParameterResolver) {
        return new ReportController(new ReportService(new ReportRepository(dataSource)), queryParameterResolver);
    }

    private ClientController createClientController(DataSource dataSource, QueryParameterResolver queryParameterResolver) {
        return new ClientController(new ClientService(new ClientRepository(dataSource)), queryParameterResolver);
    }

    private UserController createUserController(DataSource dataSource, QueryParameterResolver queryParameterResolver) {
        return new UserController(new UserService(new UserRepository(dataSource)), queryParameterResolver);
    }
}
