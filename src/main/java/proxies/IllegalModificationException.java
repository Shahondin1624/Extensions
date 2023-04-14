package proxies;

@SuppressWarnings("unused")
public class IllegalModificationException extends RuntimeException {
    public IllegalModificationException() {
    }

    public IllegalModificationException(String pattern, Object... args) {
        this(String.format(pattern, args));
    }

    public IllegalModificationException(String message) {
        super(message);
    }

    public IllegalModificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalModificationException(Throwable cause) {
        super(cause);
    }

    public IllegalModificationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
