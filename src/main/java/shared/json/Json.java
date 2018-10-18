package shared.json;

import util.shared.Strings;
import util.shared.delegate.Func;
import util.shared.linq.Linq;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Json {

	public static <T extends JsonSerializable> List<T> clone(List<T> from, Func<T> itemCreator) {
		List<T> clone = new ArrayList<>();
		for (T item : from) {
			clone.add(copy(item, itemCreator.run()));
		}
		return clone;
	}

	public static <T extends JsonSerializable> T copy(T from, T to) {
		JsonValue content = write(from);
		to.readJson(content);
		return to;
	}

	public static <T extends JsonSerializable> T read(String json, T item) {
		if (Strings.isNullOrEmpty(json)) {
			return item;
		}
		JsonValue js = JsonParser.parse(json);
		return read(js, item);
	}

	public static <T extends JsonSerializable> T read(JsonValue json, T item) {
		if (json != null && !json.isNull()) {
			item.readJson(json);
		}
		return item;
	}

	public static <T extends JsonSerializable> T readNullable(String json, Func<T> itemCreator) {
		if (Strings.isNullOrEmpty(json)) {
			return null;
		}
		JsonValue js = JsonParser.parse(json);
		if (!js.isNull()) {
			T item = itemCreator.run();
			item.readJson(js);
			return item;
		}
		return null;
	}

	public static <T extends JsonSerializable> List<T> readList(String jsonArray, Func<T> itemCreator) {
		if (Strings.isNullOrEmpty(jsonArray)) {
			return new ArrayList<>();
		}
		JsonArray array = JsonParser.parse(jsonArray).asArray();
		if (array != null) {
			return array.getList(itemCreator);
		}
		else {
			return  new ArrayList<>();
		}
	}

	public static <T extends JsonSerializable> List<T> readFromStringList(List<String> jsonList, Func<T> itemCreator) {
		List<T> result = new ArrayList<>();
		for (String json : jsonList) {
			JsonValue js = JsonParser.parse(json);
			T item = itemCreator.run();
			item.readJson(js);
			result.add(item);
		}
		return result;
	}

	/**
	 * Serialise the given object to Json.
	 */
	public static JsonValue write(JsonSerializable item) {
		if (item == null) {
			return JsonNull.Instance;
		}
		return item.writeJson();
	}

    public static JsonArray write(Collection<? extends JsonSerializable> items) {
        return JsonArray.create().add(items);
    }

    public static String writeString(JsonSerializable item) {
        return write(item).toJsonString();
    }

	public static String writeString(Collection<? extends JsonSerializable> items) {
		return write(items).toJsonString();
	}

    public static List<String> writeToStringList(Collection<? extends JsonSerializable> items) {
        return Linq.from(items).select(item -> item.writeJson().toJsonString()).toList();
    }
}
