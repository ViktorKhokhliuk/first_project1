package epam.project.app.logic.repository;

import epam.project.app.logic.entity.user.Client;
import epam.project.app.logic.entity.user.Inspector;
import epam.project.app.logic.entity.user.User;
import epam.project.app.logic.entity.user.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

@RequiredArgsConstructor
public class UserRepository {
    private final DataSource dataSource;
    private static final String SELECT_USER_BY_LOGIN = "select * from user left join client on user.id=client.id where user.login= ?;";

    @SneakyThrows
    public Optional<User> getUserByLogin(String login) {
        User foundUser;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                long id = resultSet.getLong("id");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                if (role.equals("INSPECTOR")) {
                    foundUser = new Inspector();
                    foundUser.setUserRole(UserRole.INSPECTOR);
                } else {
                    String name = resultSet.getString("name");
                    String surname = resultSet.getString("surname");
                    String itn = resultSet.getString("itn");
                    foundUser = new Client(name, surname, itn);
                    foundUser.setUserRole(UserRole.CLIENT);
                }
                foundUser.setId(id);
                foundUser.setLogin(login);
                foundUser.setPassword(password);
                return Optional.of(foundUser);
            }
            return Optional.empty();
        }
    }
}
