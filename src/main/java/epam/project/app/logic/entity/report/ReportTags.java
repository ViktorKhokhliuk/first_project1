package epam.project.app.logic.entity.report;

public enum ReportTags {
    REPORT("report"),
    PERSON("person"),
    NATIONALITY("nationality"),
    YEAR("year"),
    QUARTER("quarter"),
    MONTH("month"),
    GROUP("group"),
    ACTIVITY("activity"),
    INCOME("income");

    private final String parameter;

    ReportTags(String value) {
        this.parameter = value;
    }

    public String getValue() {
        return parameter;
    }
}
