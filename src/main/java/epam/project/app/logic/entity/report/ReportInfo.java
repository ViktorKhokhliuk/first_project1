package epam.project.app.logic.entity.report;

public enum ReportInfo {
    PROCESS("Report in processing"),
    ACCEPT("Report was accepted"),
    EDIT("Report was edited");

    private final String title;

    ReportInfo(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
