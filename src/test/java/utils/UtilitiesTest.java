package utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.Utilities.formatNanos;

public class UtilitiesTest {

    @Test
    public void testFormatNanosTest() {
        String expected = "";
        long durationInNanos = createDuration(1, 0, 0, 0, 0, 0);
        String formatResult = formatNanos(durationInNanos);
        expected += "0001.";
        assertEquals(expected, formatResult);
        durationInNanos = createDuration(1, 1, 0, 0, 0, 0);
        formatResult = formatNanos(durationInNanos);
        expected += "01:";
        assertEquals(expected, formatResult);
        durationInNanos = createDuration(1, 1, 1, 0, 0, 0);
        formatResult = formatNanos(durationInNanos);
        expected += "01:";
        assertEquals(expected, formatResult);
        durationInNanos = createDuration(1, 1, 1, 1, 0, 0);
        formatResult = formatNanos(durationInNanos);
        expected += "01.";
        assertEquals(expected, formatResult);
        durationInNanos = createDuration(1, 1, 1, 1, 1, 0);
        formatResult = formatNanos(durationInNanos);
        expected += "001:";
        assertEquals(expected, formatResult);
        durationInNanos = createDuration(1, 1, 1, 1, 1, 1);
        formatResult = formatNanos(durationInNanos);
        expected += "0000001";
        assertEquals(expected, formatResult);
    }

    protected long createDuration(long days, long hours, long minutes, long seconds, long milliseconds, long nanoseconds) {
        long result = TimeUnit.DAYS.toNanos(days);
        result += TimeUnit.HOURS.toNanos(hours);
        result += TimeUnit.MINUTES.toNanos(minutes);
        result += TimeUnit.SECONDS.toNanos(seconds);
        result += TimeUnit.MILLISECONDS.toNanos(milliseconds);
        result += nanoseconds;
        return result;
    }

    @Test
    public void testErrorLogging() {
        try {
            String read = Files.readString(Path.of("/test"));
        } catch (IOException e) {
            String stackTrace = Utilities.logError(e);
            assertStringContains(stackTrace, NoSuchFileException.class.getName());
            assertStringContains(stackTrace, "/test");
        }
    }

    protected void assertStringContains(String containing, String contained) {
        Assertions.assertNotNull(containing);
        Assertions.assertNotNull(contained);
        Assertions.assertTrue(containing.contains(contained));
    }
}