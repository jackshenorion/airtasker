package shared.delegate;

/**
 * A function with one parameter.
 *
 * @param <P>
 * @param <T>
 */
//@JsFunction
@FunctionalInterface
public interface UnaryFunc<P, T> {

    T run(P param);

}
