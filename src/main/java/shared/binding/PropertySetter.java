package shared.binding;

/**
 * For writing value to an object.
 *
 * @param <T>
 * @param <P>
 */
//@FunctionalInterface can be re-enabled when Android supports Java 8
public interface PropertySetter<T, P> {

	/**
	 * Update the object that is behind the Binding interface.
	 */
	void set(T obj, P newValue);

}
