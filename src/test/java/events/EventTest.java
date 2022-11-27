package events;

import framework.AbstractTest;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class EventTest extends AbstractTest {
    private final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    protected AbstractEventHandler mockHandler(EventType handledType) {
        AbstractEventHandler mock = Mockito.mock(AbstractEventHandler.class);
        Mockito.when(mock.getHandledEventType()).thenReturn(handledType);
        return mock;
    }

    @Test
    public void testDispatching() {
        EventTest test = new EventTest();
        TestEventDispatcher dispatcher = new TestEventDispatcher();
        Thread dispatcherThread = new Thread(dispatcher);
        dispatcherThread.start();
        AbstractEventHandler handler1 = test.mockHandler(EventTypes.DEFAULT);
        AbstractEventHandler handler2 = test.mockHandler(EventTypes.ERROR);
        dispatcher.registerEventHandler(handler1);
        dispatcher.registerEventHandler(handler2);
        Event event1 = new TestEvent(EventTypes.DEFAULT);
        TestEvent event2 = new TestEvent(EventTypes.ERROR);
        dispatcher.publish(event1);
        dispatcher.publish(event2);

        //Sleep because dispatching will happen async
        test.sleep(100);
        test.verifyHandleWasCalledXTimes(handler1, 1);
        test.verifyHandleWasCalledXTimes(handler2, 1);
        dispatcher.requestShutdown();
        log.info("Test completed successfully...");
    }

    protected ArgumentMatcher<Event> forType(EventType type) {
        return argument -> type.getName().equals(argument.getEventType().getName());
    }

    protected void verifyHandleWasCalledXTimes(EventHandler handler, int times) {
        Mockito.verify(handler, Mockito.times(times)).handle(Mockito.argThat(forType(handler.getHandledEventType())));
    }
}
