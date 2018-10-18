package shared.text;

public interface StringConverter<T> {
	
	/**
	 * Format an object to a string.
	 */
	public String format(T value);
	
	/**
	 * Parse object from a string. This method is optional for implementation.
	 */
	public T parse(String value);

}
