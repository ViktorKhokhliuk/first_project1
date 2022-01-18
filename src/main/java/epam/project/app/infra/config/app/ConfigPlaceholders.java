package epam.project.app.infra.config.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import epam.project.app.infra.web.Placeholder;
import epam.project.app.infra.web.QueryParameterResolver;
import epam.project.app.logic.controller.UserController;
import epam.project.app.logic.repository.UserRepository;
import epam.project.app.logic.service.UserService;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class ConfigPlaceholders {
    public List<Placeholder> config(DataSource dataSource,ObjectMapper objectMapper) {
        List<Placeholder> placeholders = new ArrayList<>();

        UserController userController = createUserController(dataSource, objectMapper);
        placeholders.add(new Placeholder("POST", "login", userController::login));
        placeholders.add(new Placeholder("GET", "logout", userController::logout));
        return placeholders;
    }

    private UserController createUserController(DataSource dataSource, ObjectMapper objectMapper) {
        return new UserController(new UserService(new UserRepository(dataSource)), new QueryParameterResolver(objectMapper));
    }
}
