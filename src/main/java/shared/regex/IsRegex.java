package shared.regex;

import util.shared.delegate.UnaryFunc;

import java.util.List;

/**
 * A thin layer on top of various regexp implementations. It aims to help writing shared code between
 * server side, client side and Android.
 *
 * - Server side / Android implementation is based on java.util.regex.
 * - Client side implementation is based on com.google.gwt.regexp.shared.RegExp.
 */
public abstract class IsRegex {

    /**
     * Compiles a pattern and create an instance.
     */
    public static IsRegex compile(String pattern) { return provider.run(pattern); }

    /**
     * Split text with this pattern.
     */
    public abstract List<String> split(String text);

    /**
     * Matches the pattern against input.
     */
    public abstract IsMatcher test(String input);

    /**
     * Initialise implementation provider.
     */
    public static void init(UnaryFunc<String, IsRegex> provider) { IsRegex.provider = provider; }
    private static UnaryFunc<String, IsRegex> provider;

}
