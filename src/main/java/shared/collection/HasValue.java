package shared.collection;

public class HasValue<T> {
	
	private T val;
	
	public HasValue() {
	}
	
	public HasValue(T val) {
		set(val);
	}
	
	public T get() {
		return val;
	}
	
	public void set(T obj) {
		this.val = obj;
	}

}

