package epam.project.app.logic.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportUpdateDto {

    private Long id;
    private Integer page;
    private String title;
    private String status;
    private String info;
    private String statusFilter;
    private Long clientId;
    private String clientLogin;
    private String date;
    private String type;
    private String clientName;
    private String surname;
    private String itn;

}
