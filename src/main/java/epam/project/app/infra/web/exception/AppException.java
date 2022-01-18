package epam.project.app.infra.web.exception;

public class AppException extends RuntimeException {
    public AppException(String ex) {
        super(ex);
    }
}
