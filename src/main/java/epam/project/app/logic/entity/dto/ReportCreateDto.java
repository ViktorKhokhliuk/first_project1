package epam.project.app.logic.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public class ReportCreateDto {
    @Getter
    private String title;
    @Getter
    private String path;
    @Getter
    private Long clientId;
    @Getter
    private String type;
}
