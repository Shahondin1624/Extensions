package once;

import unsafe.UnsafeRunnable;
import unsafe.UnsafeSupplier;

import java.util.Optional;

/**
 * Provides an easy way to ensure code is only called once. Can return a value or not, depending on requirements.
 * By default, checked exceptions are disabled, all exception handling has to be done manually
 *
 * @param <T> type of optional return value
 */
public interface Once<T> {
    Optional<T> call();

    boolean wasAlreadyCalled();

    static <T> Once<T> from(UnsafeSupplier<T> fromSupplier) {
        return new OnceInternal<>(fromSupplier);
    }

    static Once<?> from(UnsafeRunnable fromRunnable) {
        return new OnceInternal<>(() -> {
            fromRunnable.run();
            return null;
        });
    }

    class OnceInternal<T> implements Once<T> {
        private boolean hasAlreadyBeenCalled = false;
        private final UnsafeSupplier<T> callable;

        public OnceInternal(UnsafeSupplier<T> callable) {
            this.callable = callable;
        }

        @Override
        public Optional<T> call() {
            if (!hasAlreadyBeenCalled) {
                hasAlreadyBeenCalled = true;
                return Optional.ofNullable(callable.get());
            }
            return Optional.empty();
        }

        @Override
        public boolean wasAlreadyCalled() {
            return hasAlreadyBeenCalled;
        }
    }
}
