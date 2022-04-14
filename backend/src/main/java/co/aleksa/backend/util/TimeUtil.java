package co.aleksa.backend.util;

import co.aleksa.backend.api.dto.TimeDto;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public final class TimeUtil {

    private static final int MILLISECONDS_IN_SECOND = 1000;
    private static Long TIME_OFFSET = 0L;

    private TimeUtil() {}

    public static TimeDto calculateTimeDifference(ZonedDateTime start, ZonedDateTime end) {

        final long startSeconds = start.toEpochSecond();
        final long endSeconds = end.toEpochSecond();

        long totalDurationDelta = endSeconds - startSeconds;
        long totalDuration = totalDurationDelta;

        final long days = TimeUnit.DAYS.convert(totalDurationDelta, TimeUnit.SECONDS);
        totalDurationDelta = totalDurationDelta - TimeUnit.SECONDS.convert(days, TimeUnit.DAYS);

        final long hours = TimeUnit.HOURS.convert(totalDurationDelta, TimeUnit.SECONDS);
        totalDurationDelta = totalDurationDelta - TimeUnit.SECONDS.convert(hours, TimeUnit.HOURS);

        final long minutes = TimeUnit.MINUTES.convert(totalDurationDelta, TimeUnit.SECONDS);

        return new TimeDto(Math.toIntExact(days), Math.toIntExact(hours), Math.toIntExact(minutes), totalDuration * MILLISECONDS_IN_SECOND);
    }

    public static long calculateMillisecondsDifference(ZonedDateTime start, ZonedDateTime end) {
        final long startMillis = start.toInstant().toEpochMilli();
        final long endMillis = end.toInstant().toEpochMilli();

        return endMillis - startMillis;
    }

    public static TimeDto convertMillisToTimeDifference(long millis) {
        long millisDelta = millis;

        final long days = TimeUnit.DAYS.convert(millisDelta, TimeUnit.MILLISECONDS);
        millisDelta = millisDelta - TimeUnit.MILLISECONDS.convert(days, TimeUnit.DAYS);

        final long hours = TimeUnit.HOURS.convert(millisDelta, TimeUnit.MILLISECONDS);
        millisDelta = millisDelta - TimeUnit.MILLISECONDS.convert(hours, TimeUnit.HOURS);

        final long minutes =
                TimeUnit.MINUTES.convert(millisDelta, TimeUnit.MILLISECONDS) < 1 ? 1 : TimeUnit.MINUTES.convert(millisDelta, TimeUnit.MILLISECONDS);

        return new TimeDto(Math.toIntExact(days), Math.toIntExact(hours), Math.toIntExact(minutes), millis);
    }

    public static ZonedDateTime now() {
        return ZonedDateTime.now(ZoneId.of("UTC")).plus(TIME_OFFSET, ChronoUnit.MILLIS);
    }

    public static Long getTimeOffset() {
        return TIME_OFFSET;
    }

    public static void setTimeOffset(Long offset) {
        TIME_OFFSET = offset;
    }

    public static void clearTimeOffset() {
        TIME_OFFSET = 0L;
    }
}
