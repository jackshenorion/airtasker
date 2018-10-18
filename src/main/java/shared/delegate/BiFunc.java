package shared.delegate;

/**
 * A function with two parameters.
 *
 * @param <P1>
 * @param <P2>
 * @param <T>
 */
//@JsFunction
@FunctionalInterface
public interface BiFunc<P1, P2, T> {

    T run(P1 p1, P2 p2);

}
