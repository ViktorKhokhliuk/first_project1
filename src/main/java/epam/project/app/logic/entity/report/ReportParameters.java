package epam.project.app.logic.entity.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportParameters {

    private String person;
    private String nationality;
    private String year;
    private String quarter;
    private String month;
    private String group;
    private String activity;
    private String income;

}
