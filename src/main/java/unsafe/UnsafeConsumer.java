package unsafe;

import java.util.function.Consumer;

@FunctionalInterface
public interface UnsafeConsumer<T> extends Consumer<T> {
    @Override
    default void accept(T t) {
        try {
            unsafeAccept(t);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    void unsafeAccept(T t) throws Throwable;
}
