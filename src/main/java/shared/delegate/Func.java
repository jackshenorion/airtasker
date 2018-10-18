package shared.delegate;

/**
 * A function with no parameter.
 *
 */
//@JsFunction
@FunctionalInterface
public interface Func<T> {
	
	T run();

}
