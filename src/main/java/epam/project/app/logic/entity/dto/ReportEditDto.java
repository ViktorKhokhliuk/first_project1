package epam.project.app.logic.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ReportEditDto {
    @Getter
    private Long id;
    @Getter
    private Integer page;
    @Getter
    private String title;
    @Getter
    private String statusFilter;
    @Getter
    private Long clientId;
    @Getter
    private String date;
    @Getter
    private String type;
    @Getter
    private String person;
    @Getter
    private String nationality;
    @Getter
    private String year;
    @Getter
    private String quarter;
    @Getter
    private String month;
    @Getter
    private String group;
    @Getter
    private String activity;
    @Getter
    private String income;
}
