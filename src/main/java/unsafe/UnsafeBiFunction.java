package unsafe;

import java.util.function.BiFunction;

@FunctionalInterface
public interface UnsafeBiFunction<T, U, R> extends BiFunction<T, U, R> {
    @Override
    default R apply(T t, U u) {
        try {
            return unsafeApply(t, u);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    R unsafeApply(T t, U u) throws Throwable;
}
