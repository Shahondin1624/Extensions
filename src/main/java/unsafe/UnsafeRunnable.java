package unsafe;

@FunctionalInterface
public interface UnsafeRunnable extends Runnable, Unsafe {
    void runUnsafe() throws Throwable;

    default void run() {
        try {
            runUnsafe();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
