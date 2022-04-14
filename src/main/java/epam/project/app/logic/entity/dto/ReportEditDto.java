package epam.project.app.logic.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportEditDto {

    private Long id;
    private Integer page;
    private String title;
    private String statusFilter;
    private Long clientId;
    private String date;
    private String type;
    private String person;
    private String nationality;
    private String year;
    private String quarter;
    private String month;
    private String group;
    private String activity;
    private String income;
}
