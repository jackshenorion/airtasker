package shared.delegate;

/**
 * An action with two parameters.
 *
 * @param <P1>
 * @param <P2>
 */
//@JsFunction
@FunctionalInterface
public interface BiAction<P1, P2> {

    void run(P1 p1, P2 p2);
}
