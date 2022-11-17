package exceptions;

@FunctionalInterface
public interface ExceptionHandler {
    void handleThrowable(Throwable t);
}
