package shared.json;


public final class JsonNull extends JsonValue {

	public static final JsonNull Instance = new JsonNull();

	private JsonNull() {
	}

	@Override
	public JsonNull asNull() { return this; }

	@Override
	public boolean isNull() { return true; }

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public String toJsonString() {
		return "null";
	}

}
