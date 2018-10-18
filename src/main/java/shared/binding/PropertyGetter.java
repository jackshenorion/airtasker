package shared.binding;

/**
 * For retrieving value from an object.
 */
//@FunctionalInterface can be re-enabled when Android supports Java 8
public interface PropertyGetter<T, P> {

    /**
     * Get property value from the object.
     */
    P get(T obj);

}
