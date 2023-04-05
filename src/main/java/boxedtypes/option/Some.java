package boxedtypes.option;

import unsafe.UnsafeConsumer;
import unsafe.UnsafeFunction;

import java.util.Objects;

public record Some<E>(E value) implements Option<E> {
    public Some {
        Objects.requireNonNull(value, "Null values are forbidden");
    }

    @Override
    public void onValue(UnsafeConsumer<E> consumer) {
        Objects.requireNonNull(consumer, "consumer mustn't be null").accept(value);
    }

    @Override
    public <R> Option<R> map(UnsafeFunction<E, R> mapper) {
        return new Some<>(Objects.requireNonNull(mapper, "mapper mustn't be null").apply(value));
    }
}
