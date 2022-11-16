package unsafe;

import java.util.function.BiPredicate;

@FunctionalInterface
public interface UnsafeBiPredicate<T, U> extends BiPredicate<T, U> {
    @Override
    default boolean test(T t, U u) {
        try {
            return unsafeTest(t, u);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    boolean unsafeTest(T t, U u) throws Throwable;
}
