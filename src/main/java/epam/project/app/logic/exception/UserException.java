package epam.project.app.logic.exception;

import epam.project.app.infra.web.exception.AppException;

public class UserException  extends AppException {
    public UserException(String message) {
        super(message);
    }
}
