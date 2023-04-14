package proxies;

public interface Lockable<T> {
    void lock();

    boolean isLocked();

    T locked();
}
