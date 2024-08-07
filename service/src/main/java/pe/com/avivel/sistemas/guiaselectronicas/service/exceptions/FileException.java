package pe.com.avivel.sistemas.guiaselectronicas.service.exceptions;

public class FileException extends RuntimeException {

	private static final long serialVersionUID = -3385408043402577102L;

	public FileException() {}

    public FileException(String message) {
        super(message);
    }

    public FileException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileException(Throwable cause) {
        super(cause);
    }

    public FileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
