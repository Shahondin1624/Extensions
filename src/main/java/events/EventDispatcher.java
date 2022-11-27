package events;

public interface EventDispatcher {
    <E extends Event> void publish(E event);

    <E extends Event> void dispatch(E event);

    void registerEventHandler(EventHandler eventHandler);

    void unregisterEventHandler(EventHandler eventHandler);

    void unregisterAll();
}
