package utils;

import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

public class Utilities {
    public static String formatNanos(long nanos) {
        long days = TimeUnit.NANOSECONDS.toDays(nanos);
        nanos -= TimeUnit.DAYS.toNanos(days);
        long hours = TimeUnit.NANOSECONDS.toHours(nanos);
        nanos -= TimeUnit.HOURS.toNanos(hours);
        long minutes = TimeUnit.NANOSECONDS.toMinutes(nanos);
        nanos -= TimeUnit.MINUTES.toNanos(minutes);
        long seconds = TimeUnit.NANOSECONDS.toSeconds(nanos);
        nanos -= TimeUnit.SECONDS.toNanos(seconds);
        long milliseconds = TimeUnit.NANOSECONDS.toMillis(nanos);
        nanos -= TimeUnit.MILLISECONDS.toNanos(milliseconds);

        String dayStr = formatNumberIfNotZero(days, 4, ".");
        String hourStr = formatNumberIfNotZero(hours, 2, ":");
        String minuteStr = formatNumberIfNotZero(minutes, 2, ":");
        String secondStr = formatNumberIfNotZero(seconds, 2, ".");
        String millisecondStr = formatNumberIfNotZero(milliseconds, 3, ":");
        String nanosecondStr = formatNumberIfNotZero(nanos, 7, "");
        return String.format("%s%s%s%s%s%s", dayStr, hourStr, minuteStr, secondStr, millisecondStr, nanosecondStr);
    }

    private static String formatNumberIfNotZero(long num, int leadingZeroes, String postfix) {
        String formatterTemplate = buildFormatterTemplate(leadingZeroes);
        return num == 0 ? "" : String.format(formatterTemplate, num, postfix);
    }

    private static String buildFormatterTemplate(int leadingZeroes) {
        String formatterTemplate = "%0";
        formatterTemplate += String.format("%d", leadingZeroes);
        formatterTemplate += "d%s";
        return formatterTemplate;
    }

    public static String getStackTraceAsString(Throwable t) {
        StringJoiner stackTrace = new StringJoiner("\n");
        StackTraceElement[] elements = t.getStackTrace();
        for (int i = elements.length - 1; i >= 0; i--) {
            StackTraceElement element = elements[i];
            stackTrace.add(element.toString());
        }
        return stackTrace.toString();
    }

    public static String logError(Throwable t) {
        return t.getClass().getName() + ": " + t.getMessage() + "\n" + getStackTraceAsString(t);
    }
}
