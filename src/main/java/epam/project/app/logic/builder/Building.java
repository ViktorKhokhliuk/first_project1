package epam.project.app.logic.builder;

import epam.project.app.logic.entity.dto.ReportEditDto;

public interface Building {

    boolean build(ReportEditDto reportEditDto, String path);
}
