package events;

import java.util.Objects;

public abstract class AbstractEventHandler implements EventHandler {
    private final EventType handledType;

    protected AbstractEventHandler(EventType handledType) {
        this.handledType = Objects.requireNonNull(handledType);
    }

    @Override
    public EventType getHandledEventType() {
        return handledType;
    }

    @SuppressWarnings("unchecked")
    protected <T extends Event> T castTo(Event event) {
        if (event != null) {
            EventType eventType = event.getEventType();
            if (canHandle(eventType)) {
                return (T) event;
            } else
                throw new ClassCastException(String.format("Could not cast %s to an event of type %s!", event, eventType));
        } else throw new IllegalArgumentException("Event mustn't be null!");
    }

    protected boolean canHandle(Event event) {
        if (event != null) {
            EventType eventType = event.getEventType();
            return handledType.equals(eventType);
        }
        return false;
    }

    protected boolean canHandle(EventType eventType) {
        if (eventType != null) {
            return handledType.equals(eventType);
        }
        return false;
    }

}
