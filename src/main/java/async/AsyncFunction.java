package async;

import unsafe.UnsafeFunction;

import java.util.concurrent.CompletableFuture;

@FunctionalInterface
public interface AsyncFunction<T, R> extends UnsafeFunction<T, R>, AsyncExecutable {
    default CompletableFuture<R> future(T t) {
        return CompletableFuture.supplyAsync(() -> apply(t));
    }

    @Asynchronous
    default R await(T t) {
        return future(t).join();
    }
}
