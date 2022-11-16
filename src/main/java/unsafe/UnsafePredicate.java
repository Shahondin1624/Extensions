package unsafe;

import java.util.function.Predicate;

@FunctionalInterface
public interface UnsafePredicate<T> extends Predicate<T> {
    @Override
    default boolean test(T t) {
        try {
            return unsafeTest(t);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    boolean unsafeTest(T t) throws Throwable;
}
