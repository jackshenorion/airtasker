package shared.text;

import util.shared.collection.Lazy;
import util.shared.regex.IsMatcher;
import util.shared.regex.IsRegex;

/**
 * A parser that can convert number range in the format of
 * 
 * <pre>
 *    range number := segment(,segment)*
 *    segment := (number|range)
 *    number  := integer
 *    range   := number-number
 * </pre>
 * 
 * into an integer array.
 * 
 * For example,
 * <pre>
 *    String range = "-4--2, -1-1, 3 - 5 , 7 , 10, -100--99";
 *    int[] numbers = NumberRangeParser.parse(range);
 *    print(numbers); // -4, -3, -2, -1, 0, 1, 3, 4, 5, 7, 10, -100, -99
 * </pre>
 * 
 * @author  .gao
 * @version 1.0
 */
public class NumberRangeParser {

    public static final String M = "(\\+|-)?\\d+";
    public static final String M2N = "(" + M + ")-(" + M + ")";

    private static final Lazy<IsRegex> PATTERN_M    = Lazy.of(() -> IsRegex.compile(M));
    private static final Lazy<IsRegex> PATTERN_MTON = Lazy.of(() -> IsRegex.compile(M2N));
    private static final Lazy<IsRegex> PATTERN_ALL  = Lazy.of(() -> IsRegex.compile("(" + M + "|" + M2N + ")" +
                "(,(" + M + "|" + M2N + "))*"));

    /**
     * Returns whether the given string is valid number range.
     */
    public static boolean isValid(String s) {
        return PATTERN_ALL.get().test(s).matches();
    }

    /**
     * Parse number range into an array of integers.
     */
    public static int[] parse(String numberRange) {
        numberRange = numberRange.replace(" ", "");
        IntArray buffer = new IntArray();

        String[] segments = numberRange.split(",");
        for (int i = 0; i < segments.length; i++) {
            buffer.add(parseSegment(segments[i]));
        }

        return buffer.toArray();
    }

    static int[] parseSegment(String segment) {

        if (PATTERN_M.get().test(segment).matches()) {
            return new int[] { Integer.parseInt(segment.trim()) };
        }

        IsMatcher matcher = PATTERN_MTON.get().test(segment);
        if (matcher.matches()) {
            int m = Integer.parseInt(matcher.getGroup(1));
            int n = Integer.parseInt(matcher.getGroup(3));

            if (m > n) throw new RuntimeException("Invalid Range [" + m + "-" + n + "]");

            int[] a = new int[(n - m + 1)]; // include N
            for (int i = 0; i < a.length; i++) {
                a[i] = m + i;
            }
            return a;
        }

        throw new RuntimeException("Illegal segment: " + segment);
    }

    private static class IntArray {

        final int InitialCapacity = 16;

        int[] buffer;

        int size = 0;

        public IntArray() {
            buffer = new int[InitialCapacity];
        }

        public void add(int i) {
            ensureCapacity(buffer.length + 1);
            buffer[size] = i;
            size++;
        }

        public void add(int[] a) {
            ensureCapacity(buffer.length + a.length);
            System.arraycopy(a, 0, buffer, size, a.length);
            size += a.length;
        }

        private void ensureCapacity(int capacity) {
            if (capacity > buffer.length) {
                int[] bigger = new int[Math.max(Math.min(buffer.length * 2, 512), capacity)];
                System.arraycopy(buffer, 0, bigger, 0, buffer.length);
                buffer = bigger;
            }
        }

        public int[] toArray() {
            int[] a = new int[size];
            System.arraycopy(buffer, 0, a, 0, size);
            return a;
        }

    }

}
