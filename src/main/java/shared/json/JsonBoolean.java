package shared.json;


public final class JsonBoolean extends JsonValue {
	
	public static final JsonBoolean True = new JsonBoolean(true);
	public static final JsonBoolean False = new JsonBoolean(false);
	
	private boolean value;
	
	private JsonBoolean(boolean value) {
		this.value = value;
	}

	public boolean getValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	@Override
	public JsonBoolean asBoolean() { return this; }

	@Override
	public boolean isBoolean() { return true; }

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj instanceof JsonBoolean) == false) {
			return false;
		}
		return value == ((JsonBoolean) obj).value;
	}

	@Override
	public int hashCode() {
		return value ? 1 : 0;
	}
	
	public String toJsonString() {
		return value ? "true" : "false";
	}
	
	public static JsonBoolean get(boolean b) {
		return b ? True : False;
	}

}
