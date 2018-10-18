package shared.date;


import java.util.Date;

/**
 * This class is an alternative of date using minute as time unit. By cutting off seconds and milliseconds, a ShortDate
 * can be save as or converted to a 32 bit integer which can greatly improve serialization performance comparing to standard
 * ISO8601 date. ShortDate can be easily converted to Date, vice versa.
 *
 * Note: ShortDate is immutable.
 */
public final class ShortDate implements Comparable<ShortDate> {

    private int timeInMinutes;
    private Date javaDate;

    public ShortDate() {
        timeInMinutes = getTotalMinutes(System.currentTimeMillis());
    }

    public ShortDate(int timeInMinutes) {
        this.timeInMinutes = timeInMinutes;
    }

    public ShortDate(Date date) {
        this.timeInMinutes = getTotalMinutes(date.getTime());
    }

    public ShortDate(ShortDate date) {
        this.timeInMinutes = date.getTimeInMinutes();
    }

    public boolean after(ShortDate date) {
        return timeInMinutes > date.timeInMinutes;
    }

    public boolean before(ShortDate date) {
        return timeInMinutes < date.timeInMinutes;
    }

    public ShortDate addMinutes(int minutes) {
        return new ShortDate(timeInMinutes + minutes);
    }

    public ShortDate addHours(int hours) {
        return new ShortDate(timeInMinutes + hours * 60);
    }

    public ShortDate addDays(int days) {
        return new ShortDate(timeInMinutes + days * 24 * 60);
    }

    public int getTimeInMinutes() {
        return timeInMinutes;
    }

    public Date toDate() {
        if (javaDate == null) {
            javaDate = new Date(timeInMinutes * 60000L); // must multiple by a 64bit number.
        }
        return javaDate;
    }

    @Override
    public int compareTo(ShortDate date) {
        return timeInMinutes - date.timeInMinutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShortDate shortDate = (ShortDate) o;
        return timeInMinutes == shortDate.timeInMinutes;
    }

    @Override
    public int hashCode() {
        return timeInMinutes;
    }

    private static int getTotalMinutes(long dateTime) {
        return (int)(dateTime / 60000);
    }

    /**
     * A safe function to convert ShortDate to Date with null pointer handled properly.
     */
    public static Date toDate(ShortDate date) {
        return date != null ? date.toDate() : null;
    }

    /**
     * A safe function to convert Date to ShortDate with null pointer handled properly.
     */
    public static ShortDate toShortDate(Date date) {
        return date != null ? new ShortDate(date) : null;
    }

}
