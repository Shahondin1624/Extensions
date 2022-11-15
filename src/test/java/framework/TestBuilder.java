package framework;

import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TestBuilder<X> {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Consumer<X> test;
    private final Supplier<X> supplier;
    private Consumer<Throwable> exceptionHandling;

    protected TestBuilder(Consumer<X> test, Supplier<X> supplier) {
        this.supplier = supplier;
        if (test == null) {
            Assertions.fail(new IllegalStateException("No test specified!"));
        }
        this.test = test;
    }

    public void failOnException() {
        exceptionHandling = Assertions::fail;
        run();
    }

    public void failOnException(String message) {
        exceptionHandling = throwable -> Assertions.fail(message, throwable);
        run();
    }

    public void succeedOnException() {
        exceptionHandling = throwable -> {
        };
    }

    public <T extends Throwable> void succeedOnException(Class<T> expectedExceptionType) {
        exceptionHandling = throwable -> {
            if (!(throwable.getClass() == expectedExceptionType)) {
                Assertions.fail(String.format("Exception was of type %s, instead of %s", throwable.getClass(), expectedExceptionType));
            }
        };
        run();
    }

    public <T extends Throwable> void succeedOnExceptionSubtypeOf(Class<T> superType) {
        exceptionHandling = throwable -> {
            if (!(superType.isAssignableFrom(throwable.getClass()))) {
                Assertions.fail(String.format("Exception was not a subtype of %s, instead is %s", superType.getName(), throwable.getClass().getName()));
            }
        };
        run();
    }

    public void executeOnException(Consumer<Throwable> exceptionHandler) {
        exceptionHandling = exceptionHandler;
        run();
    }

    protected void run() {
        try {
            test.accept(supplier.get());
        } catch (Throwable t) {
            log.debug("{}: {}", t.getClass().getName(), t.getMessage());
            exceptionHandling.accept(t);
        }
    }
}
