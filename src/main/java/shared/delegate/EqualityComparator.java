package shared.delegate;

/**
 * A comparator for comparing equality of two objects.
 */
//@JsFunction
@FunctionalInterface
public interface EqualityComparator<T> {
	
	boolean equals(T x, T y);
}
