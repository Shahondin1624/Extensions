package unsafe;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
@Deprecated
public class Result2<E> {
    private E ok;
    private Throwable err;

    private Result2(Supplier<E> func) {
        if (func == null) {
            err = new IllegalArgumentException("The passed function was null!");
            return;
        }
        try {
            ok = func.get();
        } catch (Throwable t) {
            err = t;
        }
    }

    public Optional<E> getResult() {
        return Optional.ofNullable(ok);
    }

    public Optional<Throwable> getError() {
        return Optional.ofNullable(err);
    }


    public E getResultDirectly() throws NoResultPresentException {
        if (ok == null) {
            throw new NoResultPresentException(err);
        } else {
            return ok;
        }
    }

    public Throwable getErrorDirectly() throws NoErrorPresentException {
        if (err == null) {
            throw new NoErrorPresentException();
        } else {
            return err;
        }
    }

    public boolean hasResult() {
        return ok != null;
    }

    public boolean hasError() {
        return err != null;
    }

    public <F> F unwrapResult(Function<E, F> function) throws NoResultPresentException {
        return getResult().map(function).orElseThrow(() -> new NoResultPresentException(err));
    }

    public <F> Result2<F> unwrap(UnsafeFunction<E, F> function) {
        return new Result2<>(() -> function.apply(ok));
    }

    public void unwrapException(UnsafeConsumer<Throwable> consumer) {
        if (consumer == null) {
            throw new IllegalArgumentException("consumer was null!");
        }
        consumer.accept(err);
    }

    public <F> Optional<F> handle(UnsafeFunction<E, F> okHandler, Consumer<Throwable> errHandler) {
        if (hasResult()) {
            return Optional.ofNullable(okHandler.apply(ok));
        } else {
            errHandler.accept(err);
            return Optional.empty();
        }
    }

    public static <E> Result2<E> of(UnsafeSupplier<E> func) {
        return new Result2<>(func);
    }
}
