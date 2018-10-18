package shared.date;

import util.shared.Strings;
import util.shared.collection.Lazy;
import util.shared.regex.IsMatcher;
import util.shared.regex.IsRegex;

public class DateParser {

    private static Lazy<IsRegex> DATE_PATTERN 	    = Lazy.of(() -> IsRegex.compile("^(\\d{4})\\-(\\d{1,2})\\-(\\d{1,2})$"));
    private static Lazy<IsRegex> TIME_PATTERN 		= Lazy.of(() -> IsRegex.compile("^(\\d{1,2}):(\\d{1,2}):(\\d{1,2})$"));
    private static Lazy<IsRegex> DATE_SPAN_PATTERN = Lazy.of(() -> IsRegex.compile("^(\\+|-)(\\d{1,3})-(\\d{1,2})-(\\d{1,2})$"));

    public static String format(MutableDate date) {
        return date.getYear() + "-" + leadingZero(date.getMonth() + 1) + "-"  + leadingZero(date.getDay());
    }

    public static String format(MutableDate date, String returnIfNull) {
        if (date == null) {
            return returnIfNull;
        }
        return date.getYear() + "-" + leadingZero(date.getMonth() + 1) + "-"  + leadingZero(date.getDay());
    }

    public static String format(MutableTime time) {
        return leadingZero(time.getHour()) + ":" + leadingZero(time.getMinute()) + ":"  + leadingZero(time.getSecond());
    }

    /**
     * Parse a date String.
     */
    public static MutableDate parseDate(String dateString) {
        if (Strings.isNullOrEmpty(dateString)) {
            return null;
        }
        try {
            IsMatcher matcher = DATE_PATTERN.get().test(dateString);
            if (matcher.matches()) {
                return new MutableDate(
                    Integer.parseInt(matcher.getGroup(1)),
                    Integer.parseInt(matcher.getGroup(2)) - 1, // MutableDate.Month is between 0 and 11
                    Integer.parseInt(matcher.getGroup(3))
                );
            }
        }
        catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Parse a date string in date format or in date span format.
     */
    public static HasDate parseDateOrSpan(String dateString) {
        if (Strings.isNullOrEmpty(dateString)) {
            return null;
        }
        try {
            char prefix = dateString.charAt(0);
            if (prefix == '-' || prefix == '+') {
                return parseDateSpan(dateString);
            }
            return parseDate(dateString);
        }
        catch (Exception ignored) {
            return null;
        }
    }

    /**
     * Parse a date span String.
     */
    public static DateSpan parseDateSpan(String dateString) {
        if (Strings.isNullOrEmpty(dateString)) {
            return null;
        }

        try {
            IsMatcher matcher = DATE_SPAN_PATTERN.get().test(dateString);
            if (matcher.matches()) {
                int sign = matcher.getGroup(1).startsWith("+") ? 1 : -1;
                return new DateSpan(
                    Integer.parseInt(matcher.getGroup(2)) * sign,
                    Integer.parseInt(matcher.getGroup(3)) * sign,
                    Integer.parseInt(matcher.getGroup(4)) * sign
                );
            }
        }
        catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Parse a time String in the format of "hh:mm:ss"
     */
    public static MutableTime parseTime(String timeString) {
        if (Strings.isNullOrEmpty(timeString)) {
            return null;
        }

        try {
            IsMatcher matcher = TIME_PATTERN.get().test(timeString);
            if (matcher.matches()) {
                return new MutableTime(
                    Integer.parseInt(matcher.getGroup(1)),
                    Integer.parseInt(matcher.getGroup(2)),
                    Integer.parseInt(matcher.getGroup(3))
                );
            }
        }
        catch (Exception ignored) {
        }
        return null;
    }

    private static String leadingZero(int i) {
        return i < 10 ? "0" + i : Integer.toString(i);
    }
}
