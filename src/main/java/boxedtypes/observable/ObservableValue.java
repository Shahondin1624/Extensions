package boxedtypes.observable;

import boxedtypes.box.Box;

import java.util.ArrayList;
import java.util.List;

/**
 * Allows storing a type and sharing it safely. When its internal value changes, it notifies its observers
 *
 * @param <E>
 */
public class ObservableValue<E> extends Box<E> implements Observable<ObservableValue<E>> {
    private final List<Observer<ObservableValue<E>>> registeredObservers = new ArrayList<>();

    @Override
    public void subscribeObserver(Observer<ObservableValue<E>> observer) {
        registeredObservers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer<ObservableValue<E>> observer : registeredObservers) {
            observer.onUpdate(this);
        }
    }

    @Override
    public void unsubscribeObserver(Observer<ObservableValue<E>> observer) {
        registeredObservers.remove(observer);
    }

    @Override
    public void setValue(E value) {
        super.setValue(value);
        notifyObservers();
    }
}
