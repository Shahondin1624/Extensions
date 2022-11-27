package events;

public class TestEvent implements Event {
    private final EventType type;

    public TestEvent(EventType type) {
        this.type = type;
    }

    @Override
    public EventType getEventType() {
        return type;
    }
}
