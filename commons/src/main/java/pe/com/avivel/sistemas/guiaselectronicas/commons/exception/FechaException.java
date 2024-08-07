package pe.com.avivel.sistemas.guiaselectronicas.commons.exception;

public class FechaException extends Exception{

    public FechaException() {

    }

    public FechaException(String message) {
        super(message);
    }

    public FechaException(String message, Throwable cause) {
        super(message, cause);
    }

    public FechaException(Throwable cause) {
        super(cause);
    }

    protected FechaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
