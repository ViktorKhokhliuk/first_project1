package epam.project.app.logic.entity.dto;

import epam.project.app.logic.entity.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientRegistrationDto {

    private String login;
    private String password;
    private String name;
    private String surname;
    private String itn;
    private UserRole userRole = UserRole.CLIENT;
}
