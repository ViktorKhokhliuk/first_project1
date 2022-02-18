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
    private String name;
    @Getter
    private String status;
    @Getter
    private String info;
    @Getter
    private String statusFilter;
    @Getter
    private Long clientId;
    @Getter
    private String date;
    @Getter
    private String type;
    @Getter
    private String report;
}
