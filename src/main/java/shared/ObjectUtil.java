package shared;


import util.shared.delegate.EqualityComparator;

public class ObjectUtil {

    /**
     * Compares two comparable objects. Null objects are considered to be equal.
     *
     * @return a negative integer, zero, or a positive integer as the first
     *         argument is less than, equal to, or greater than the second.
     */
    public static final int compares(Comparable o1, Comparable o2) {
        if (o1 == null && o2 == null)
            return 0;
        if (o1 == null)
            return -1;
        if (o2 == null)
            return 1;
        else
            return o1.compareTo(o2);
    }

    /**
     * Compares two decimal values within a positive delta.
     *
     * @return a negative integer, zero, or a positive integer as the first
     *         argument is less than, equal to, or greater than the second.
     */
    public static final int compares(double n1, double n2, double delta) {
        double diff = n1 - n2;
        return Math.abs(diff) < delta ? 0 : diff < 0 ? -1 : 1;
    }

    /**
     * This method is equivalent to Objects.equals which isn't emulated in Android SDK.
     */
    public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

    /**
     * Tests whether two objects are equal. Null objects are considered to be equal.
     */
    public static <T> boolean equals(T o1, T o2, EqualityComparator<T> comparator) {
        if (o1 == null)
            return o2 == null;
        if (o2 == null)
            return false;
        return comparator.equals(o1, o2);
    }

    /**
     * Tests whether its two decimal numbers are equal to within a positive delta.
     * Null objects are considered to be equal.
     *
     * @return true if the two arguments equal; false otherwise.
     */
    public static boolean equals(Double n1, Double n2, double delta) {
        if (n1 == null)
            return n2 == null;
        if (n2 == null)
            return false;

        return equals(n1.doubleValue(), n2.doubleValue(), delta);
    }

    /**
     * Tests whether its two decimal numbers are equal to within a positive delta.
     */
    public static boolean equals(double n1, double n2, double delta) {
        return Math.abs(n1 - n2) < delta;
    }

    /**
     * Convert a string to true/false. It returns true if the string equals "true" or "yes";
     * returns false if the string equals "false" or "no"; returns the second argument otherwise.
     */
    public static boolean toBool(Object o, boolean returnIfFail) {
        if (o == null) return returnIfFail;

        String s = o.toString();
        if (s.length() == 0) {
            return returnIfFail;
        }
        if (s.equals("true") || s.equals("yes")) {
            return true;
        }
        if (s.equals("false") || s.equals("no")) {
            return false;
        }
        return returnIfFail;
    }

    /**
     * Convert a string to Boolean.TRUE/Boolean.FALSE. It returns Boolean.TRUE if the string equals "true" or "yes";
     * returns Boolean.FALSE if the string equals "false" or "no"; returns the second argument otherwise.
     */
    public static Boolean toBoolean(Object o, Boolean returnIfFail) {
        if (o == null) return returnIfFail;

        String s = o.toString();
        if (s.length() == 0) {
            return returnIfFail;
        }

        if (s.equals("true") || s.equals("yes")) {
            return Boolean.TRUE;
        }
        if (s.equals("false") || s.equals("no")) {
            return Boolean.FALSE;
        }
        else {
            return returnIfFail;
        }
    }

    /**
     * Convert a string to a number. The second argument is returned if the conversion fails.
     */
    public static double toDouble(Object o, double returnIfFail) {
        if (o == null) return returnIfFail;

        String s = o.toString();
        if (s.length() == 0) {
            return returnIfFail;
        }

        try {
            double d = Double.parseDouble(s);
            return Double.isInfinite(d) ? returnIfFail : d;
        }
        catch (NumberFormatException e) {
            return returnIfFail;
        }
    }

    /**
     * Convert a string to a number. The second argument is returned if the conversion fails.
     */
    public static Double toDouble(Object o, Double returnIfFail) {
        if (o == null) return returnIfFail;

        String s = o.toString();
        if (s.length() == 0) {
            return returnIfFail;
        }

        try {
            double d = Double.parseDouble(s);
            return Double.isInfinite(d) ? returnIfFail : d;
        }
        catch (NumberFormatException e) {
            return returnIfFail;
        }
    }

    /**
     * Convert a string to an integer. The second argument is returned if the conversion fails.
     */
    public static int toInt(Object o, int returnIfFail) {
        if (o == null) return returnIfFail;

        String s = o.toString();
        if (s.length() == 0) {
            return returnIfFail;
        }

        try {
            return Integer.parseInt(s);
        }
        catch (NumberFormatException e) {
            return returnIfFail;
        }
    }

    /**
     * Convert a string to an integer. The second argument is returned if the conversion fails.
     */
    public static Integer toInt(Object o, Integer returnIfFail) {
        if (o == null) return returnIfFail;

        String s = o.toString();
        if (s.length() == 0) {
            return returnIfFail;
        }

        try {
            return Integer.parseInt(s);
        }
        catch (NumberFormatException e) {
            return returnIfFail;
        }
    }

    /**
     * Convert a string to a long integer. The second argument is returned if the conversion fails.
     */
    public static long toLong(Object o, long returnIfFail) {
        if (o == null) return returnIfFail;

        String s = o.toString();
        if (s.length() == 0) {
            return returnIfFail;
        }

        try {
            return Long.parseLong(s);
        }
        catch (NumberFormatException e) {
            return returnIfFail;
        }
    }

    public static Long toLong(Object o, Long returnIfFail) {
        if (o == null) return returnIfFail;

        String s = o.toString();
        if (s.length() == 0) {
            return returnIfFail;
        }

        try {
            return Long.parseLong(s);
        }
        catch (NumberFormatException e) {
            return returnIfFail;
        }
    }
}