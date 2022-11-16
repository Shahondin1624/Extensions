package unsafe;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface UnsafeBiConsumer<T, U> extends BiConsumer<T, U> {
    @Override
    default void accept(T t, U u) {
        try {
            unsafeAccept(t, u);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    void unsafeAccept(T t, U u) throws Throwable;
}
