package unsafe;

import java.io.Serial;

public class NoResultPresentException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -2492296416427028117L;

    public NoResultPresentException() {
    }

    public NoResultPresentException(String message) {
        super(message);
    }

    public NoResultPresentException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoResultPresentException(Throwable cause) {
        super(cause);
    }

    public NoResultPresentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
