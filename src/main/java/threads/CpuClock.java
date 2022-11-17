package threads;

import javax.inject.Inject;
import java.util.concurrent.atomic.AtomicLong;

public class CpuClock {
    private final TimeInterface time;
    private long startTimeNanos;
    private final AtomicLong runTime = new AtomicLong(0);
    private final AtomicLong lastStart = new AtomicLong(0);

    @Inject
    public CpuClock(TimeInterface time) {
        this.time = time;
    }

    public void start() {
        startTimeNanos = time.getCurrentNanos();
        lastStart.set(time.getCurrentNanos());
    }

    public long stop() {
        assertClockHasBeenStartedBefore();
        long stopTime = time.getCurrentNanos();
        long thisRunTime = stopTime - lastStart.get();
        runTime.addAndGet(thisRunTime);
        return thisRunTime;
    }

    public long getRuntimeDuration() {
        assertClockHasBeenStartedBefore();
        return runTime.get();
    }

    private void assertClockHasBeenStartedBefore() {
        if (lastStart.get() == 0) {
            throw new IllegalStateException("Clock has not been started before!");
        }
    }

    public long getStartTimeNanos() {
        assertClockHasBeenStartedBefore();
        return startTimeNanos;
    }

    public long getCurrentRuntimeDuration() {
        assertClockHasBeenStartedBefore();
        long now = time.getCurrentNanos();
        return now - startTimeNanos;
    }
}
