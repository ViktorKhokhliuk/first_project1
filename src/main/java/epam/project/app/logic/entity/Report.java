package epam.project.app.logic.entity;

import lombok.Data;

@Data
public class Report {
    private Long id;
    private String description;
    private Long client_id;
}
