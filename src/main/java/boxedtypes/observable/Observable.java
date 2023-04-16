package boxedtypes.observable;

/**
 * Allows registering observers that can be notified when the observed value changes
 *
 * @param <O>
 */
public interface Observable<O extends Observable<O>> {
    void subscribeObserver(Observer<O> observer);

    void notifyObservers();

    void unsubscribeObserver(Observer<O> observer);
}
