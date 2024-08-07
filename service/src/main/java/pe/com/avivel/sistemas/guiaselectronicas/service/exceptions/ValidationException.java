package pe.com.avivel.sistemas.guiaselectronicas.service.exceptions;

public class ValidationException extends  RuntimeException {

    private static final long serialVersionUID = 9019417580703468911L;

    public ValidationException() {}

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }

    public ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
