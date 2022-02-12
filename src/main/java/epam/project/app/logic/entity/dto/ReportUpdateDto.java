package epam.project.app.logic.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public class ReportUpdateDto {
    @Getter
    private Long id;
    @Getter
    private String status;
    @Getter
    private Long clientId;
    @Getter
    private String clientLogin;
}
