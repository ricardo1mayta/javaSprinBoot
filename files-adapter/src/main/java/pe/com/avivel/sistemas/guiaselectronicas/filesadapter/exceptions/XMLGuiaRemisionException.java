package pe.com.avivel.sistemas.guiaselectronicas.filesadapter.exceptions;

public class XMLGuiaRemisionException extends Exception{

    public XMLGuiaRemisionException() {
        super();
    }

    public XMLGuiaRemisionException(String message) {
        super(message);
    }

    public XMLGuiaRemisionException(String message, Throwable cause) {
        super(message, cause);
    }

    public XMLGuiaRemisionException(Throwable cause) {
        super(cause);
    }

    protected XMLGuiaRemisionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
