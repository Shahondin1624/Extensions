package unsafe;

import java.io.Serial;

public class NoErrorPresentException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -406956122480880295L;

    public NoErrorPresentException() {
    }

    public NoErrorPresentException(String message) {
        super(message);
    }

    public NoErrorPresentException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoErrorPresentException(Throwable cause) {
        super(cause);
    }

    public NoErrorPresentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
