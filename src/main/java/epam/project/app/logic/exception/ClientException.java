package epam.project.app.logic.exception;

import epam.project.app.infra.web.exception.AppException;

public class ClientException extends AppException {
    public ClientException(String message) {
        super(message);
    }
}
