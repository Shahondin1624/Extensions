package boxedtypes.option;

import unsafe.UnsafeConsumer;
import unsafe.UnsafeFunction;

public sealed interface Option<E> permits Some, None {
    void onValue(UnsafeConsumer<E> consumer);

    <R> Option<R> map(UnsafeFunction<E, R> mapper);

    @SuppressWarnings("unchecked")
    static <E> Option<E> of(E nullable) {
        if (nullable != null) {
            return new Some<>(nullable);
        } else {
            return None.NONE;
        }
    }
}
