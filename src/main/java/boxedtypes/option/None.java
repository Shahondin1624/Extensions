package boxedtypes.option;

import unsafe.UnsafeConsumer;
import unsafe.UnsafeFunction;

@SuppressWarnings("rawtypes")
public final class None<E> implements Option<E> {
    public static final None NONE = new None();

    private None() {
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj != null && obj.getClass() == this.getClass();
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public String toString() {
        return "None[]";
    }

    @Override
    public void onValue(UnsafeConsumer<E> consumer) {
        //
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> Option<R> map(UnsafeFunction<E, R> mapper) {
        return NONE;
    }
}
