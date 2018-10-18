package shared.delegate;

/**
 * A function with no parameter.
 *
 */
//@JsFunction
@FunctionalInterface
public interface IntUnaryFunc<P> {
	
	int run(P param);

}
