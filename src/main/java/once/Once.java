package once;

import boxedtypes.option.Option;
import unsafe.UnsafeRunnable;
import unsafe.UnsafeSupplier;

/**
 * Provides an easy way to ensure code is only called once. Can return a value or not, depending on requirements.
 * By default, checked exceptions are disabled, all exception handling has to be done manually
 *
 * @param <T> type of optional return value
 */
public interface Once<T> {
    Option<T> call();

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
        public Option<T> call() {
            if (!hasAlreadyBeenCalled) {
                hasAlreadyBeenCalled = true;
                return Option.of(callable.get());
            }
            return Option.empty();
        }

        @Override
        public boolean wasAlreadyCalled() {
            return hasAlreadyBeenCalled;
        }
    }
}
