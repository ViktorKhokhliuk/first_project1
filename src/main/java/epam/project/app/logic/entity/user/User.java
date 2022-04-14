package epam.project.app.logic.entity.user;

import lombok.Data;

@Data
public abstract class User {

    private Long id;
    private String login;
    private String password;
    private UserRole userRole;

}
