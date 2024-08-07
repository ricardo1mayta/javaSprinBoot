package pe.com.avivel.sistemas.guiaselectronicas.filesadapter.exceptions;

public class FilesAdapterException extends Exception{

    public FilesAdapterException() {
        super();
    }

    public FilesAdapterException(String message) {
        super(message);
    }

    public FilesAdapterException(String message, Throwable cause) {
        super(message, cause);
    }

    public FilesAdapterException(Throwable cause) {
        super(cause);
    }

    protected FilesAdapterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
