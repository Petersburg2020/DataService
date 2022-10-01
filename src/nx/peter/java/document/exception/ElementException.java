package nx.peter.java.document.exception;

public abstract class ElementException extends DocumentException {
    public ElementException() {
        this("Element Exception");
    }

    public ElementException(String message) {
        super(message);
    }

    public ElementException(String message, Throwable cause) {
        super(message, cause);
    }

    public ElementException(Throwable cause) {
        super(cause);
    }

    public ElementException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
