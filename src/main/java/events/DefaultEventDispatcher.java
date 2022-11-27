package events;

import execution.ExecutionWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import threads.DaemonThread;

import java.lang.invoke.MethodHandles;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class DefaultEventDispatcher extends DaemonThread implements EventDispatcher {
    private final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Map<EventType, List<EventHandler>> handlers = new HashMap<>();
    private final Queue<Event> eventQueue = new ConcurrentLinkedQueue<>();

    public DefaultEventDispatcher(String name) {
        super(name);
    }

    public DefaultEventDispatcher(String name, ExecutionWrapper executor) {
        super(name, executor);
    }

    @Override
    public <E extends Event> void publish(E event) {
        log.info("Publishing event {} to dispatcher", event);
        eventQueue.add(event);
    }

    @Override
    public <E extends Event> void dispatch(E event) {
        if (event != null) {
            List<EventHandler> eventHandlersForType = handlers.get(event.getEventType());
            if (eventHandlersForType == null) {
                log.warn("Could not dispatch event {} as its type is not registered", event);
                return;
            }
            log.debug("Dispatching event {}...", event);
            for (EventHandler eventHandler : eventHandlersForType) {
                log.debug("Dispatching event to {}", eventHandler);
                eventHandler.handle(event);
            }
        }
    }

    @Override
    public void registerEventHandler(EventHandler eventHandler) {
        if (eventHandler != null) {
            EventType type = eventHandler.getHandledEventType();
            log.info("Registering {} for event type {}", eventHandler, type.getName());
            handlers.putIfAbsent(type, new ArrayList<>());
            handlers.get(type).add(eventHandler);
        }
    }

    @Override
    public void unregisterEventHandler(EventHandler eventHandler) {
        if (eventHandler != null) {
            boolean removed = false;
            EventType type = eventHandler.getHandledEventType();
            List<EventHandler> eventHandlers = handlers.get(type);
            if (eventHandlers != null) {
                removed = eventHandlers.remove(eventHandler);
            }
            if (removed) {
                log.info("{} was unregistered for event type {}", eventHandler, type);
            } else {
                log.warn("{} could not be unregistered for event type {}", eventHandler, type);
            }
        }
    }

    @Override
    public void unregisterAll() {
        log.info("Unregistering all event handlers...");
        handlers.values().forEach(List::clear);
    }

    @Override
    public void executeWork() {
        if (!eventQueue.isEmpty()) {
            Event polled = eventQueue.poll();
            dispatch(polled);
        }
    }
}
