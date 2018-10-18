package shared.delegate;

/**
 * An action with one parameter.
 *
 * @param <P>
 */
//@JsFunction
@FunctionalInterface
public interface UnaryAction<P> {

    void run(P p);
}
