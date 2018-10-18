package shared.delegate;

/**
 * An action with one parameter and add another Integer for the purpose of indicating the index of the item.
 *
 * @param <P>
 */
//@JsFunction
@FunctionalInterface
public interface IntBiAction<P> {

    void run(int i, P p);
}
