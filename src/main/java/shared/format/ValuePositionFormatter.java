package shared.format;

public interface ValuePositionFormatter<T> {
	
	String format(int position, T value);

}
