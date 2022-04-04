package epam.project.app.logic.entity.dto;

import epam.project.app.logic.entity.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String login;
    private String password;
    private UserRole userRole;
}
