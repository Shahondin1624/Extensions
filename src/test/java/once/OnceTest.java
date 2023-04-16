package once;

import boxedtypes.option.Option;
import org.junit.jupiter.api.Test;
import unsafe.UnsafeRunnable;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class OnceTest {
    @Test
    public void testOnlyOnceCalledWithoutReturn() {
        AtomicInteger counter = new AtomicInteger();
        Once<?> once = Once.from(new UnsafeRunnable() { //Lambda can't be used because otherwise the Compiler would infer the type UnsafeSupplier<T>
            @Override
            public void runUnsafe() {
                counter.getAndIncrement();
            }
        });
        assertFalse(once.wasAlreadyCalled());
        once.call();
        assertEquals(1, counter.get());
        once.call();
        assertEquals(1, counter.get());
        assertTrue(once.wasAlreadyCalled());
    }

    @Test
    public void testOnlyOnceCalledWithReturn() {
        AtomicInteger counter = new AtomicInteger();
        Once<Integer> once = Once.from(counter::incrementAndGet);
        assertFalse(once.wasAlreadyCalled());
        int result = once.call().orElseThrow(() -> new IllegalStateException("Should return 1 here"));
        assertEquals(1, result);
        assertEquals(1, counter.get());
        boolean notCalled = once.call().isEmpty();
        assertTrue(notCalled);
        assertEquals(1, counter.get());
        assertTrue(once.wasAlreadyCalled());
    }

    @Test
    public void testReturnIsEmptyWhenNoValueExpected() {
        AtomicInteger counter = new AtomicInteger();
        Once<?> once = Once.from(new UnsafeRunnable() { //Lambda can't be used because otherwise the Compiler would infer the type UnsafeSupplier<T>
            @Override
            public void runUnsafe() {
                counter.getAndIncrement();
            }
        });
        Option<?> result = once.call();
        assertTrue(result.isEmpty());
    }
}