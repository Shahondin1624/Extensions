package async;

import unsafe.UnsafeConsumer;

import java.util.concurrent.CompletableFuture;

@FunctionalInterface
public interface AsyncConsumer<T> extends UnsafeConsumer<T>, AsyncExecutable {
    default CompletableFuture<Void> future(T t) {
        return CompletableFuture.runAsync(() -> accept(t));
    }

    @Asynchronous
    default void await(T t) {
        future(t).join();
    }
}
