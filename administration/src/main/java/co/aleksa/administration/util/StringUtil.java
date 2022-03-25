package co.aleksa.administration.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

public final class StringUtil {

    private static final MathContext MATH_CONTEXT = new MathContext(16);

    private StringUtil() {
        // private constructor to prevent instantiation
    }

    public static String substringBetween(String string, String open, String close) {
        if (string.contains(open) && string.contains(close)) {
            return StringUtils.substringBetween(string, open, close);
        }

        return string;
    }

    public static boolean isBoolean(String string) {
        return string.equalsIgnoreCase("true") || string.equalsIgnoreCase("false");
    }

    public static Optional<Boolean> toBoolean(String string) {
        if (isBoolean(string)) {
            return Optional.of(Boolean.valueOf(string));
        }
        return Optional.empty();
    }

    public static boolean isDecimal(String string) {
        try {
            Double.valueOf(string);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isDateTime(String string) {
        try {
            ZonedDateTime.parse(string, DateTimeFormatter.ISO_ZONED_DATE_TIME);
        } catch (DateTimeParseException dtpe) {
            return false;
        }
        return true;
    }

    public static Optional<BigDecimal> toBigDecimal(String string) {
        try {
            return Optional.of(new BigDecimal(string));
        } catch (NumberFormatException nfe) {
            return Optional.empty();
        }
    }

    public static boolean isLong(String string) {
        try {
            Long.valueOf(string);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static Optional<Long> toLong(String string) {
        try {
            return Optional.of(Long.valueOf(string));
        } catch (NumberFormatException nfe) {
            return Optional.empty();
        }
    }

    public static boolean isInteger(String string) {
        try {
            Integer.valueOf(string);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static Optional<Integer> toInteger(String string) {
        try {
            return Optional.of(Integer.valueOf(string));
        } catch (NumberFormatException nfe) {
            return Optional.empty();
        }
    }

    public static boolean isZonedDateTime(String string) {
        try {
            return toZonedDateTime(string).isPresent();
        } catch (DateTimeParseException nfe) {
            return false;
        }
    }

    public static boolean isDate(String string) {
        return toZonedDate(string).isPresent();
    }

    public static boolean isTime(String string) {
        return toZonedTime(string).isPresent();
    }

    public static String roundAndStripZeros(BigDecimal number) {
        return number.round(MATH_CONTEXT).stripTrailingZeros().toPlainString();
    }

    public static String roundWithScale(BigDecimal number, int scale) {
        return number.setScale(scale, RoundingMode.HALF_UP).toPlainString();
    }

    public static Optional<ZonedDateTime> toZonedDateTime(String string) {
        if (string == null || string.equals("")) return Optional.empty();

        return Optional.of(ZonedDateTime.parse(string, DateTimeFormatter.ISO_ZONED_DATE_TIME));
    }

    public static Optional<ZonedDateTime> toZonedDate(String string) {
        if (string == null || string.equals("")) return Optional.empty();

        return Optional.of(LocalDate.parse(string, DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay(ZoneOffset.UTC));
    }

    public static Optional<ZonedDateTime> toZonedTime(String string) {
        if (string == null || string.equals("")) return Optional.empty();

        return Optional.of(LocalTime.parse(string, DateTimeFormatter.ISO_LOCAL_TIME).atDate(LocalDate.of(1970, 1, 1)).atZone(ZoneOffset.UTC));
    }

    public static Optional<LocalDate> toDate(String string) {
        if (string == null) return Optional.empty();
        try {
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            final LocalDate date = LocalDate.parse(string, formatter);
            return Optional.of(date);
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    public static Optional<LocalTime> toTime(String string) {
        if (string == null) return Optional.empty();
        try {
            final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm a");
            final LocalTime time = LocalTime.parse(string, dateTimeFormatter);
            return Optional.of(time);
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    public static Optional<ZonedDateTime> parseEventDateTime(String dateTime) {
        if (dateTime != null) {
            dateTime = dateTime.replace(' ', 'T') + ZonedDateTime.now().getOffset();
            return Optional.of(ZonedDateTime.parse(dateTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        }
        return Optional.empty();
    }

    public static String shortenValueAddEllipsis(String value, Integer maxLength) {
        if (value.length() > maxLength) return value.substring(0, maxLength) + "...";
        return value;
    }
}
