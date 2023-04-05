package boxedtypes.observable;

@FunctionalInterface
public interface Observer {
    void onUpdate(Observable observed);
}
