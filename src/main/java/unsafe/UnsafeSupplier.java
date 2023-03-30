package unsafe;

import java.util.function.Supplier;

@FunctionalInterface
public interface UnsafeSupplier<T> extends Supplier<T>, Unsafe {
    @Override
    default T get() {
        try {
            return getUnsafe();
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    T getUnsafe() throws Throwable;
}
