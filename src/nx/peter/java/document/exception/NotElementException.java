package nx.peter.java.document.exception;

public class NotElementException extends ElementException {
    public NotElementException() {
        this("Not Element Exception");
    }

    public NotElementException(CharSequence name1, CharSequence name2) {
        this(name1 + " is not the same as " + name2 + "!!!");
    }

    public NotElementException(CharSequence message) {
        super(String.valueOf(message));
    }
}
