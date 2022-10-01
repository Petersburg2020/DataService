package nx.peter.java.document.exception;

public class NotItemException extends DocumentException {
    public NotItemException() {
        super();
    }

    public NotItemException(String message) {
        super(message);
    }

    public NotItemException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotItemException(Throwable cause) {
        super(cause);
    }

    public NotItemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
