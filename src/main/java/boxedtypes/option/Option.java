package boxedtypes.option;

import unsafe.UnsafeConsumer;
import unsafe.UnsafeFunction;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Attempts to mimic Rust's Option-enum. Objects of this type are best handled with pattern-matching
 *
 * @param <E> type of the optional value
 */
public sealed interface Option<E> permits None, Some {

    /**
     * When a value is present, perform the operation defined in the consumer on it
     */
    default void onValue(UnsafeConsumer<E> consumer) {
        if (this instanceof Some<E> some) {
            consumer.accept(some.value());
        }
    }

    /**
     * When a value is present, attempt to transform it with the mapper function
     *
     * @param <R> new type
     * @return an option with the new specified type
     */
    default <R> Option<R> map(UnsafeFunction<E, R> mapper) {
        Objects.requireNonNull(mapper);
        switch (this) {
            case Some<E> some -> {
                try {
                    R r = mapper.unsafeApply(some.value());
                    return of(r);
                } catch (Throwable e) {
                    return None.none();
                }
            }
            case None<E> ignored -> {
                return None.none();
            }
            default -> throw new IllegalStateException("This shouldn't be reachable!");
        }
    }

    /**
     * @param other value
     * @return the stored value or other if none is present
     */
    default E orElse(E other) {
        if (this instanceof Some<E> some) {
            return some.value();
        } else {
            return other;
        }
    }

    /**
     * Return the stored value or throw the specified exception
     *
     * @param supplier for the exception that should be thrown
     * @param <Err>    type of the exception
     * @return the stored value, if there is one
     */
    default <Err extends RuntimeException> E orElseThrow(Supplier<Err> supplier) {
        if (this instanceof Some<E> some) {
            return some.value();
        } else {
            throw supplier.get();
        }
    }

    /**
     * @return true when there is no stored value or
     * false when there is one
     */
    default boolean isEmpty() {
        return this instanceof None<E>;
    }

    /**
     * @return true when there is a stored value or
     * false when there is none
     */
    default boolean isPresent() {
        return this instanceof Some<E>;
    }

    /**
     * Construct a new Option from a value
     *
     * @param nullable the value that is stored in the new Option-object. Null values are permitted
     * @param <E>      type of the passed value
     * @return Option of nullable or None, if nullable was null
     */
    static <E> Option<E> of(E nullable) {
        if (nullable != null) {
            return new Some<>(nullable);
        } else {
            return None.none();
        }
    }

    /**
     * @return an empty Option
     */
    static <E> Option<E> empty() {
        return None.none();
    }
}
