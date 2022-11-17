package exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class RethrowExceptionHandler implements ExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void handleThrowable(Throwable t) {
        log.error("{} occurred: {}. Will be rethrown", t.getClass().getSimpleName(), t.getMessage());
        throw new RuntimeException(t);
    }
}
