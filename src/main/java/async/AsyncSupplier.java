package async;

import unsafe.UnsafeSupplier;

import java.util.concurrent.CompletableFuture;

@FunctionalInterface
public interface AsyncSupplier<T> extends UnsafeSupplier<T>, AsyncExecutable {
    default CompletableFuture<T> future() {
        return CompletableFuture.supplyAsync(() -> get())
                .exceptionally(throwable -> null);
    }

    @Asynchronous
    default T await() {
        return future().join();
    }
}
