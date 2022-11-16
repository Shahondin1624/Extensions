package unsafe;

import java.util.function.Function;

@FunctionalInterface
public interface UnsafeFunction<T, R> extends Function<T, R> {
    @Override
    default R apply(T t) {
        try {
            return unsafeApply(t);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    R unsafeApply(T t) throws Throwable;
}
