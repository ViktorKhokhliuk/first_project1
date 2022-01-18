package epam.project.app.logic.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserDTO {
    @Getter
    private String login;
    @Getter
    private String password;
}
