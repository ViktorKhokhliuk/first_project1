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

    @SneakyThrows
    public Optional<User> getUserByLogin(String login) {
        String sql = "select * from user where login=?";
        User foundUser;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet1 = preparedStatement.executeQuery();
            if (resultSet1.next()) {
                long id = resultSet1.getLong("id");
                String password = resultSet1.getString("password");
                String role = resultSet1.getString("role");
                if (role.equals("INSPECTOR")) {
                    foundUser = new Inspector();
                    foundUser.setUserRole(UserRole.INSPECTOR);
                } else {
                    foundUser = new Client();
                    foundUser.setUserRole(UserRole.CLIENT);
                }
                foundUser.setId(id);
                foundUser.setLogin(login);
                foundUser.setPassword(password);
                return Optional.of(foundUser);
            }
        }
        return Optional.empty();
    }
}
