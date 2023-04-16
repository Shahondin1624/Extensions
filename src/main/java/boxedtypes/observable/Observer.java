package boxedtypes.observable;

/**
 * Deals in some way with changes on the observable object
 *
 * @param <O> the type of the observed object
 */
@FunctionalInterface
public interface Observer<O extends Observable<O>> {
    void onUpdate(O observed);
}
