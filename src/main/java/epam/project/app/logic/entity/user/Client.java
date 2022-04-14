package epam.project.app.logic.entity.user;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class Client extends User {

    private String name;
    private String surname;
    private String itn;

    public Client(String name, String surname, String itn) {
        super();
        this.name = name;
        this.surname = surname;
        this.itn = itn;
        super.setUserRole(UserRole.CLIENT);
    }

    public Client() {
        super();
        super.setUserRole(UserRole.CLIENT);
    }
}
