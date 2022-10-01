package nx.peter.java.document.exception;

public class NonElementException extends DocumentException {
    public NonElementException() {
        this("Non Element Exception Caught!");
    }

    public NonElementException(String message) {
        super(message);
    }
}
