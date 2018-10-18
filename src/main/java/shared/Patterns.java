package shared;

import util.shared.collection.Lazy;
import util.shared.regex.IsRegex;

public class Patterns {

    /**
     * Common patterns. Note, com.google.gwt.regexp.shared.RegExp is used instead of java.util.Pattern so
     * that they can be used at both client and server sides. Under the scene, RegExp delegates the call
     * to java.util.Pattern.
     *
     * Usage example:
     * <code>
     *    MatchResult matcher = Patterns.INT.get().exec("123123"); matcher is null if the pattern doesn't match.
     *    int groupCount = matcher.getGroupCount();
     *    String group1 = matcher.getGroup(1);
     *    ...
     * </code>
     */
    public static final Lazy<IsRegex>
        INT = Lazy.of(() -> IsRegex.compile("^(\\+|-)?\\d+$")),
        FLOAT = Lazy.of(() -> IsRegex.compile("^(\\+|-)?\\d+(\\.\\d*)?$")),
        SCIENTIFIC_FLOAT = Lazy.of(() -> IsRegex.compile("^(\\+|-)?\\d*\\.\\d+((E|e)(\\+|-)?\\d*)?$" + '|' +
                    "^(\\+|-)?\\d+(\\.)?(E|e)(\\+|-)?\\d*$" + '|' +
                    "^(\\+|-)?\\d+\\.$")),
        WHITESPACE = Lazy.of(() -> IsRegex.compile("^\\s+$")),
        PASSWORD1 = Lazy.of(() -> IsRegex.compile("(?=^.{8,30}$)(?=.*[a-zA-Z])(?=.*[^a-zA-z])(?=^.*[^\\s].*$).*$")),
        PASSWORD2 = Lazy.of(() -> IsRegex.compile("(?=^.{8,30}$)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-z])(?=^.*[^\\s].*$).*$")),
        EMAIL = Lazy.of(() -> IsRegex.compile("^\\w[\\w-%#\\+\\$&'\\*/=?_{}|~`^]*(\\.[\\w-%#\\+\\$&'\\*/=?_{}|~`^]+)*@[\\w-]+(\\.[\\w-]+)+$")),
        AU_MOBILE_NUMBER = Lazy.of(() -> IsRegex.compile("^(61[\\d]{9}|0[\\d]{9})$")),
        USER_NAME  = Lazy.of(() -> IsRegex.compile("^.{0,50}$")),
        HTML_CHARS = Lazy.of(() -> IsRegex.compile("[&<>'\"]")),
        NUMBER = Lazy.of(() -> IsRegex.compile("^[0-9]*$"))
    ;



}
