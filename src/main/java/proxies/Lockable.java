package proxies;

/**
 * Interface that can be dynamically implemented by {@link Unmodifiable#extendWithLockable(Object)}
 * It will disable access to the wrapped object, after {@link Lockable#lock()} was called
 *
 * @param <T>
 */
public interface Lockable<T> {
    void lock();

    boolean isLocked();

    T locked();
}
