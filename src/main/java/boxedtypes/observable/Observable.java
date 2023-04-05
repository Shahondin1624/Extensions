package boxedtypes.observable;

public interface Observable {
    void subscribeObserver(Observer observer);

    void notifyObservers();

    void unsubscribeObserver(Observer observer);
}
