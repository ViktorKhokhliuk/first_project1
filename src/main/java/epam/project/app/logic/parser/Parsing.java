package epam.project.app.logic.parser;

import epam.project.app.logic.entity.report.ReportParameters;

public interface Parsing {

    ReportParameters parse(String path);
}
