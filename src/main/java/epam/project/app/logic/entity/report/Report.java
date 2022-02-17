package epam.project.app.logic.entity.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    private Long id;
    private String name;
    private String path;
    private String status;
    private String info;
    private String date;
    private String type;
    private Long clientId;
}
