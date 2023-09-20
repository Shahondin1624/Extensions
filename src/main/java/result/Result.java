package result;

import boxedtypes.option.None;
import boxedtypes.option.Option;
import boxedtypes.option.Some;
import unsafe.UnsafeFunction;
import unsafe.UnsafeSupplier;

import java.util.Objects;

/**
 * Attempt to mimic Rust's Result enum. Objects of this type should best be handled with pattern matching.
 *
 * @param <T>   Generic type of possible value
 * @param <Err> Generic type of possible error
 */
public sealed interface Result<T, Err extends Throwable> permits Error, Ok {
    default boolean isError() {
        return switch (this) {
            case Ok<T, Err> ok -> false;
            case Error<T, Err> err -> true;
        };
    }

    default boolean isOk() {
        return switch (this) {
            case Ok<T, Err> ok -> true;
            case Error<T, Err> err -> false;
        };
    }

    @SuppressWarnings("unchecked")
    default <U> Result<U, Err> map(UnsafeFunction<T, U> mapper) {
        Objects.requireNonNull(mapper);
        switch (this) {
            case Ok<T, Err> ok -> {
                try {
                    U u = mapper.unsafeApply(ok.ok());
                    return new Ok<>(u);
                } catch (Throwable e) {
                    return new Error<>((Err) e);
                }
            }
            case Error<T, Err> err -> {
                return new Error<>(err.error());
            }
            default -> throw new IllegalStateException("This shouldn't be reachable!");
        }
    }

    /**
     * Effectively discards the possible error-value and instead just
     * @return a {@link None} instead (or {@link Some} should a value be present)
     */
    default Option<T> asOption() {
        if (this instanceof Ok<T, Err> ok) {
            return new Some<>(ok.ok());
        } else {
            return None.none();
        }
    }

    @SuppressWarnings("unchecked")
    static <T, Err extends Throwable> Result<T, Err> of(UnsafeSupplier<T> unsafe) {
        try {
            return new Ok<>(unsafe.getUnsafe());
        } catch (Throwable e) {
            return new Error<>((Err) e);
        }
    }
}
