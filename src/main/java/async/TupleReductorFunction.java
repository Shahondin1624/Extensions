package async;

import unsafe.UnsafeFunction;

public interface TupleReductorFunction<T> extends UnsafeFunction<Tuple, T> {
    T reduceTo(Tuple tuple);

    default T unsafeApply(Tuple tuple) throws Throwable {
        return reduceTo(tuple);
    }
}
