package epam.project.app.logic.entity.user;

import epam.project.app.logic.entity.report.Report;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Client extends User {
//    private List<Report> reports;
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
