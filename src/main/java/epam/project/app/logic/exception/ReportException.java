package epam.project.app.logic.exception;

import epam.project.app.infra.web.exception.AppException;

public class ReportException extends AppException {
    public ReportException(String message) {
        super(message);
    }
}
