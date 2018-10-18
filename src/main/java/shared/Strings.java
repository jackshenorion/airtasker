package shared;

import com.google.gwt.core.shared.GwtIncompatible;
import util.shared.math.MathUtil;
import util.shared.text.XPlatformUUID;

/**
 * Created by   on 15/03/2015.
 */
public class Strings {

    /**
     * Convert the word to have the first letter in uppercase and the others in lower case.
     * @param word
     * @return
     */
    public static String capitalize(String word) {
        if (isNullOrEmpty(word)) {
            return "";
        }
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }

    /**
     * Compares two strings lexicographically. Null objects are considered to be equal.
     *
     * @return a negative, 0 or a positive as the first string is less, equal to,
     *         greater than the second string lexicographically, irrespective of
     *         case.
     */
    public static int compares(String s1, String s2) {
        return 	(s1 == null && s2 == null) ? 0 :
            (s1 != null && s2 == null) ? 1 :
                (s1 == null && s2 != null)	? -1  : s1.compareTo(s2);
    }

    /**
     * Compares two strings lexicographically, irrespective of case difference.
     * Null objects are considered to be equal.
     *
     * @return a negative, 0 or a positive as the first string is less, equal to,
     *         greater than the second string lexicographically, irrespective of
     *         case.
     */
    public static int comparesIgnoreCase(String s1, String s2) {
        return 	(s1 == null && s2 == null) ? 0 :
            (s1 != null && s2 == null) ? 1 :
                (s1 == null && s2 != null)	? -1  : s1.compareToIgnoreCase(s2);
    }

    /**
     * Returns the specified string converted to a format suitable for XML. All
     * signle-quote, double-quote, greater-than, less-than and ampersand
     * characters are replaces with their corresponding XML Character Entity
     * code. If the given string is null, the method returns an empty string.
     *
     * @param in -
     *            the String to convert
     * @return the converted string
     */
    public static String encodeXML(String in) {
        if (in == null)
            return "";

        StringBuffer result = null;
        for (int i = 0, max = in.length(), delta = 0; i < max; i++) {
            char c = in.charAt(i);
            String replacement = null;

            if (c == '&') {
                replacement = "&amp;";
            } else if (c == '<') {
                replacement = "&lt;";
            } else if (c == '\r') {
                replacement = "&#13;";
            } else if (c == '>') {
                replacement = "&gt;";
            } else if (c == '"') {
                replacement = "&quot;";
            } else if (c == '\'') {
                replacement = "&apos;";
            }

            if (replacement != null) {
                if (result == null) {
                    result = new StringBuffer(in);
                }
                result.replace(i + delta, i + delta + 1, replacement);
                delta += (replacement.length() - 1);
            }
        }
        if (result == null) {
            return in;
        }
        return result.toString();
    }

    /**
     * Tests whether the two strings are equal lexicographically, irrespective
     * of case difference, and allowing for null (two null values are considered
     * to be equal).
     *
     * @param s1 -
     *            the first string to be compared.
     * @param s2 -
     *            the second string to be compared.
     * @return true if they are equal, false otherwise.
     */
    public static boolean equalsIgnoreCase(String s1, String s2) {
        return	(s1 == null && s2 == null) ? true :
                (s1 != null) ? s1.equalsIgnoreCase(s2) : false;
    }

    /**
     * This method is equivalent to
     * <p>
     * <code>str.toLowerCase().indexOf(substring.toLowerCase());</code>
     * </p>
     *
     * @param str -
     *            the string to be searched from, must not be null.
     * @param substring -
     *            the string to be searched for, must not be null.
     * @return the index of the substring, or returns -1 if the substring is not
     *         found.
     */
    public static int indexOfIgnoreCase(String str, String substring) {
        return toNotNull(str).toLowerCase().indexOf(toNotNull(substring).toLowerCase());
    }

    /**
     * This method is equivalent to
     * <p>
     * <code>str.toLowerCase().indexOf(substring.toLowerCase(), from);</code>
     * </p>
     *
     * @param str -
     *            the string to be searched from, must not be null.
     * @param substring -
     *            the string to be searched for, must not be null.
     * @param from -
     *            the position to start searching.
     * @return the index of the substring, or returns -1 if the substring is not
     *         found.
     */
    public static int indexOfIgnoreCase(String str, String substring, int from) {
        return str.toLowerCase().indexOf(substring.toLowerCase(), from);
    }

    /**
     * Returns whether the given character is an escaped character - \n, \t, \r, \\, etc.
     *
     * @param c-
     *            a string
     * @return whether the given character represents an escape char.
     */
    public static boolean isEscapable(char c) {
        return  c == '\\' || c == 'b' || c == 'f' || c == 'n' ||
                c == 'r'  || c == 't' || c == '\''|| c == '"';
    }

    /**
     * Returns true if the string is either null or empty, false otherwise.
     *
     * @param s -
     *            a string
     * @return true if the string is null or empty, false otherwise.
     */
    public static boolean isNullOrEmpty(CharSequence s) {
        return s == null || s.length() == 0;
    }

    /**
     * Return true if the given string neither null nor empty, false otherwise.
     *
     * @param s -
     *            a string
     * @return true if the given string neither null nor empty, false otherwise.
     */
    public static boolean notNullOrEmpty(CharSequence s) {
        return s != null && s.length() > 0;
    }

    /**
     * Generate a random string based on the given pattern. Occurance of '_' in
     * the pattern is to be replaced by a random alphabetic character, occurance
     * of '#' is to be replaced by a random digit. All other characters retain.
     * <p>
     * For example, pattern "___, #### is your lucky number" may produce "XYZ,
     * 3435 is your lucky number"
     *
     * @param pattern
     * @return a random string
     */
    public static String random(String pattern) {
        StringBuilder b = new StringBuilder(pattern.length());
        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            if (c == '_') {
                int seed = Math.abs((int)(Math.random() * 52) % 52);
                b.append(seed > 25 ? 'A' + (seed - 26) : 'a' + seed);
            }
            else if (c == '#') {
                b.append((char)('0' + Math.abs((int)(Math.random() * 10)) % 10));
            }
            else {
                b.append(c);
            }
        }
        return b.toString();
    }

    public static String randomUUID() {
        return XPlatformUUID.uuid();
    }

    /**
     * This method is a safer replacement of String.substring(). It returns
     * <ul>
     * 	<li>null if the <code>s</code> is null</li>
     * 	<li>an empty string if the length of the <code>s</code> is smaller than the beginIndex</li>
     * 	<li><code>s.substring(beginIndex, s.length())</code> if the endIndex is larger than the length of the <code>s</code></li>
     * 	<li>s.substring(beginIndex, endIndex)</code> otherwise</li>
     * </ul>
     *
     * @param s - source string
     * @param beginIndex
     * @param endIndex
     * @return
     */
    public static final String substring(String s, int beginIndex, int endIndex) {
        if (s == null)
            return null;
        beginIndex = Math.max(beginIndex, 0);
        if (beginIndex > s.length() || endIndex < beginIndex)
            return "";
        if (endIndex > s.length())
            return s.substring(beginIndex);

        return s.substring(beginIndex, endIndex);
    }

    public static String toString(double d, int decimalPlaces) {
        return Double.toString(MathUtil.round(d, decimalPlaces));
    }

    /**
     * Trims a string. Returns null if the given string is null.
     *
     * @param s -
     *            the string to trim
     * @return a trimmed string or null.
     */
    public static String trim(String s) {
        return (s == null) ? null : s.trim();
    }

    public static char unescape(String str) {
        Precondition.checkEquals('\\', str.charAt(0));
        return unescape(str.charAt(1));
    }

    public static char unescape(char escaped) {
        switch(escaped) {
            case '\\':	return '\\';
            case 'b':	return '\b';
            case 'f':	return '\f';
            case 'n':	return '\n';
            case 'r':	return '\r';
            case 't':	return '\t';
            case '\'':	return '\'';
            case '"':	return '"';
        }
        throw new IllegalArgumentException("illegal escape char");
    }

    /**
     * Generate hexadecimal in lower case.
     */
    @GwtIncompatible
    public static String toHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int k = 0; k < bytes.length; k++ ) {
            int v = bytes[k] & 0xFF;
            hexChars[k * 2] = Hex[v >>> 4];
            hexChars[k * 2 + 1] = Hex[v & 0x0F];
        }
        return new String(hexChars);
    }

    private final static char[] Hex = "0123456789abcdef".toCharArray();


    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }



    /**
     * Return the given string if it is not null, or an empty string if the given string is null.
     */
    public static String toNotNull(String s) {
        return (s == null) ? "" : s;
    }

    /**
     * Return the given string if it is not null, or the 2nd parameter if the given string is null.
     */
    public static String toNotNull(String s, String returnIfNull) {
        return (s == null) ? returnIfNull : s;
    }

    /**
     * Returns the result of <code>o.toString()</code>, or an empty string if the object is null.
     */
    public static String toNotNull(Object o) {
        return o == null ? "" : o.toString();
    }
}
