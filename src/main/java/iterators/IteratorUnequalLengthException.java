package iterators;

public class IteratorUnequalLengthException extends RuntimeException {
    public IteratorUnequalLengthException() {
        super("Iterators were not the same length!");
    }

    public IteratorUnequalLengthException(String message) {
        super(message);
    }

    public IteratorUnequalLengthException(String message, Throwable cause) {
        super(message, cause);
    }

    public IteratorUnequalLengthException(Throwable cause) {
        super(cause);
    }

    public IteratorUnequalLengthException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
