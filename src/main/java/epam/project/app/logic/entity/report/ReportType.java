package epam.project.app.logic.entity.report;

public enum ReportType {
    INCOME_STATEMENT("income statement"), //Справка о доходах
    INCOME_TAX("income tax"), //подоходный налог
    SINGLE_TAX("single tax"); //единый налог

    private String title;

    ReportType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
