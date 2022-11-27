package events;

public interface EventHandler {
    void handle(Event event);

    EventType getHandledEventType();
}
