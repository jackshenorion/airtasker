package shared.json;

import util.shared.Strings;

/**
 * A super class of JSON parser implementations. You are suggested to use {@link Json} which provides high level
 * JSON functions.
 */
public abstract class JsonParser {

	static JsonParser impl = null;

	public static boolean isValidJson(String json) {
		if (Strings.isNullOrEmpty(json)) {
			return false;
		}
		try {
			parse(json);
			return true;
		}
		catch (Exception error) {
			return false;
		}
	}

	public static JsonValue parse(String json) {
        if (Strings.isNullOrEmpty(json)) {
            return JsonNull.Instance;
        }

        assert impl != null : "JsonParser has not initialized";
		return impl.parseJson(json);
	}

	protected abstract JsonValue parseJson(String text);

	protected abstract JsonObject createObject();

	protected abstract JsonArray createArray();

    public static void set(JsonParser impl) {
		JsonParser.impl = impl;
    }

}
