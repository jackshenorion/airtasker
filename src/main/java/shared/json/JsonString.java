package shared.json;


import util.shared.Strings;

public final class JsonString extends JsonValue {
	
	String value;
	
    public JsonString(String value) {
		setValue(value);
	}
    
    public String getValue() {
		return value;
	}
    
    public void setValue(String value) {
		this.value = value != null ? value : "";
	}

    @Override
	public boolean equals(Object other) {
		if ((other instanceof JsonString) == false) {
			return false;
		}
		return value.equals(((JsonString) other).value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public JsonString asString() { return this; }

	@Override
	public boolean isString() { return true; }

	@Override
	public boolean isEmpty() {
		return Strings.isNullOrEmpty(value);
	}

	@Override
    public String toJsonString() {
        return encode(value);
    }

	private static String encode(String data) {
		if (data == null) {
			return "null";
		}

		StringBuffer output = new StringBuffer(100);
		output.append('"');
		for (int i = 0, n = data.length(); i < n; ++i) {
			final char c = data.charAt(i);
			switch (c) {
			case '\\':
			case '"':
				output.append('\\').append(c);
				break;
			case '\b':
				output.append("\\b");
				break;
			case '\t':
				output.append("\\t");
				break;
			case '\n':
				output.append("\\n");
				break;
			case '\f':
				output.append("\\f");
				break;
			case '\r':
				output.append("\\r");
				break;
			default:
				// TODO(knorton): The json.org code encodes ranges of characters
				// in the form u####. Given that JSON is supposed to be UTF-8, I
				// don't understand why you would want to do that.
				output.append(c);
			}
		}
		output.append('"');
		return output.toString();
	}

}
