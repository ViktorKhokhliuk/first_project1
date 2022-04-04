package epam.project.app.logic.entity.dto;

import epam.project.app.logic.entity.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ClientRegistrationDto {
    @Getter
    private String login;
    @Getter
    private String password;
    @Getter
    private String name;
    @Getter
    private String surname;
    @Getter
    private String itn;
    @Getter
    private UserRole userRole = UserRole.CLIENT;

}
