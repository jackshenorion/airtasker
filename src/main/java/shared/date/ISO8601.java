package shared.date;

import util.shared.Strings;

import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by   on 31/05/2015.
 */
public abstract class ISO8601 {

    public static String formatDate(Date date) {
        return date != null ? get().format(date) : "";
    }

    public static Date parseDate(String json) {
        if (Strings.isNullOrEmpty(json)) {
            return null;
        }
        return get().parse(json);
    }

    protected abstract String format(Date date);
    protected abstract Date parse(String text);

    public static void set(ISO8601 impl) {
        ISO8601.impl = impl;
    }

    private static ISO8601 get() {
        assert impl != null : "ISO8601 has not initialized";
        return impl;
    }

    protected static Logger logger = Logger.getLogger("ISO8601");
    private static ISO8601 impl = null;

}
