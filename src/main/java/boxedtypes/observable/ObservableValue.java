package boxedtypes.observable;

import boxedtypes.box.Box;

import java.util.ArrayList;
import java.util.List;

public class ObservableValue<E> extends Box<E> implements Observable {
    private final List<Observer> registeredObservers = new ArrayList<>();

    @Override
    public void subscribeObserver(Observer observer) {
        registeredObservers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : registeredObservers) {
            observer.onUpdate(this);
        }
    }

    @Override
    public void unsubscribeObserver(Observer observer) {
        registeredObservers.remove(observer);
    }
}
