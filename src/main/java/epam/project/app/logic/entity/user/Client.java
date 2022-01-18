package epam.project.app.logic.entity.user;

import epam.project.app.logic.entity.Report;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Client extends User {
    private List<Report> reports;

    public Client() {
        super();
    }
}
