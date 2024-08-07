package pe.com.avivel.sistemas.guiaselectronicas.efact.exceptions;

public class EfactException extends Exception{

    public EfactException() {
        super();
    }

    public EfactException(String message) {
        super(message);
    }

    public EfactException(String message, Throwable cause) {
        super(message, cause);
    }

    public EfactException(Throwable cause) {
        super(cause);
    }

    protected EfactException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
