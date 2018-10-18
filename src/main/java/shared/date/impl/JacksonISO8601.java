package shared.date.impl;

import com.fasterxml.jackson.databind.util.ISO8601Utils;
import com.google.gwt.core.shared.GwtIncompatible;
import util.shared.date.ISO8601;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;

/**
 * Jackson based ISO8601 parser can be used on server and in Android but is incompatible with GWT.
 * You must not use this class directly.
 */
@GwtIncompatible
public final class JacksonISO8601 extends ISO8601 {

    @Override
    protected Date parse(String text) {
        try {
            return ISO8601Utils.parse(text, new ParsePosition(0));
        }
        catch (ParseException e) {
            logger.warning("Invalid date: " + text);
            return null;
        }
    }

    @Override
    protected String format(Date date) {
        return ISO8601Utils.format(date);
    }
}
