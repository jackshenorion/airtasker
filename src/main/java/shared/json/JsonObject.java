package shared.json;


import util.shared.Enums;
import util.shared.date.DateParser;
import util.shared.date.ISO8601;
import util.shared.date.MutableDate;
import util.shared.date.ShortDate;
import util.shared.delegate.Func;
import util.shared.delegate.UnaryFunc;
import util.shared.math.MathUtil;
import util.shared.primitive.Val;

import java.util.*;
import java.util.logging.Logger;

public abstract class JsonObject extends JsonValue {

    private static final int MaxDecimalPlaces = 6;

    public static JsonObject create() {
        return JsonParser.impl.createObject();
    }

    protected static Logger logger = Logger.getLogger("JsonObject");

    @Override
	public JsonObject asObject() { return this; }

    @Override
    public boolean isObject() { return true; }

    public abstract Iterable<String> getKeys();

    public abstract JsonValue get(String key);

    public abstract int size();

    public JsonArray getArray(String key) {
        return get(key).asArray();
    }

    public Boolean getBoolean(String key) {
        JsonBoolean v = get(key).asBoolean();
        return v != null ? v.getValue() : null;
    }

    public boolean getBoolean(String key, boolean returnIfNull) {
        JsonBoolean v = get(key).asBoolean();
        return v != null ? v.getValue() : returnIfNull;
    }

    public Date getDate(String key) {
        return getDate(key, null);
    }

    public Date getDate(String key, Date returnIfNull) {
        Date date = ISO8601.parseDate(getString(key));
        return date != null ? date : returnIfNull;
    }

    public Double getDouble(String key) {
        JsonNumber v = get(key).asNumber();
        return v != null ? v.getValue() : null;
    }

    public double getDouble(String key, double returnIfNull) {
        JsonNumber v = get(key).asNumber();
        return v != null ? v.getValue() : returnIfNull;
    }

    public Integer getInt(String key) {
        JsonNumber v = get(key).asNumber();
        return v != null ? (int) v.getValue() : null;
    }

    public <T extends Enum<T>> T getEnum(String key, Class<T> enumType) {
        return getEnum(key, enumType, null);
    }

    public <T extends Enum<T>> T getEnum(String key, Class<T> enumType, T returnIfNull) {
        JsonString v = get(key).asString();
        return v != null ? Enums.valueOf(enumType, v.getValue(), returnIfNull) : returnIfNull;
    }

    public <T extends Enum<T>> List<T> getEnums(String key, Class<T> enumType) {
        JsonArray array = getArray(key);
        if (array != null) {
            return array.getEnums(enumType);
        }
        else {
            return new ArrayList<>();
        }
    }

    public int getInt(String key, int returnIfNull) {
        JsonNumber v = get(key).asNumber();
        return v != null ? (int) v.getValue() : returnIfNull;
    }

    public JsonObject getJsonObject(String key) {
        return get(key).asObject();
    }

    public MutableDate getMutableDate(String key) {
        return getMutableDate(key, null);
    }

    public MutableDate getMutableDate(String key, MutableDate returnIfNull) {
        MutableDate date = DateParser.parseDate(getString(key));
        return date != null ? date : returnIfNull;
    }

    public <T extends JsonSerializable> T getSerializable(String key, T obj) {
        JsonValue v = get(key);
        if (!v.isNull()) {
            obj.readJson(v);
        }
        return obj;
    }

    public <T extends JsonSerializable> T getSerializableIfPresent(String key, Func<T> objCreator) {
        JsonValue v = get(key);
        if (v.isNull()) {
            return null;
        }
        T instance = objCreator.run();
        instance.readJson(v);
        return instance;
    }

    public ShortDate getShortDate(String key) {
        return getShortDate(key, null);
    }

    public ShortDate getShortDate(String key, ShortDate returnIfNull) {
        int timeInMinutes = getInt(key, -1);
        return timeInMinutes >= 0 ? new ShortDate(timeInMinutes) : returnIfNull;
    }

    public String getString(String key) {
        return getString(key, "");
    }

    public String getString(String key, String returnIfNull) {
        JsonString v = get(key).asString();
        return v != null ? v.getValue().trim() : returnIfNull;
    }

    public <T extends JsonSerializable> List<T> getList(String key, Func<T> itemCreator) {
        JsonArray array = getArray(key);
        if (array != null) {
            return array.getList(itemCreator);
        }
        else {
            return  new ArrayList<>();
        }
    }

    public <T> List<T> getListIfPresent(String key, UnaryFunc<JsonValue, T> itemConverter) {
        JsonArray array = getArray(key);
        if (array != null) {
            return array.getListIfPresent(itemConverter);
        }
        else {
            return new ArrayList<>();
        }
    }

    public List<String> getStrings(String key) {
        JsonArray array = getArray(key);
        if (array != null) {
            return array.getStrings();
        }
        else {
            return new ArrayList<>();
        }
    }

    public List<Integer> getIntegers(String key) {
        JsonArray array = getArray(key);
        if (array != null) {
            return array.getIntegers();
        }
        else {
            return new ArrayList<>();
        }
    }

    public List<Double> getDoubles(String key) {
        JsonArray array = getArray(key);
        if (array != null) {
            return array.getDoubles();
        }
        else {
            return new ArrayList<>();
        }
    }

    /**
     * Get value with the given key and try converting the value to null, boolean, number, text, array or map.
     */
    public Val getValue(String key) {
        JsonValue v = get(key);
        return JsonUtil.toVal(v);
    }

    public List<Val> getValues(String key) {
        Val v = getValue(key);
        return v.isList() ? v.asList() : Collections.emptyList();
    }

    public abstract JsonObject put(String key, JsonValue jsonValue);

    public JsonObject put(String key, boolean v) {
        put(key, JsonBoolean.get(v));
        return this;
    }

    public JsonObject put(String key, Boolean v) {
        if (v != null) {
            put(key, JsonBoolean.get(v));
        }
        return this;
    }

    public JsonObject put(String key, Date v) {
        if (v != null) {
            put(key, new JsonString(ISO8601.formatDate(v)));
        }
        return this;
    }

    public JsonObject put(String key, MutableDate v) {
        if (v != null) {
            put(key, new JsonString(DateParser.format(v)));
        }
        return this;
    }

    public JsonObject put(String key, int v) {
        put(key, new JsonNumber(v));
        return this;
    }

    public JsonObject put(String key, Integer v) {
        if (v != null) {
            put(key, new JsonNumber(v));
        }
        return this;
    }

    public JsonObject put(String key, double v) {
        put(key, new JsonNumber(MathUtil.round(v, MaxDecimalPlaces)));
        return this;
    }

    public JsonObject put(String key, Double v) {
        if (v != null) {
            put(key, new JsonNumber(MathUtil.round(v, MaxDecimalPlaces)));
        }
        return this;
    }

    public JsonObject put(String key, ShortDate v) {
        if (v != null) {
            put(key, new JsonNumber(v.getTimeInMinutes()));
        }
        return this;
    }

    public JsonObject put(String key, String v) {
        if (v != null && v.length() > 0) {
            put(key, new JsonString(v));
        }
        return this;
    }

    public JsonObject put(String key, JsonSerializable v) {
        if (v != null) {
            JsonValue js = v.writeJson();
            if (!js.isNull() && !js.isEmpty()) {
                put(key, js);
            }
        }
        return this;
    }

    public JsonObject put(String key, Enum<?> v) {
        if (v != null) {
            put(key, new JsonString(v.name()));
        }
        return this;
    }

    public JsonObject put(String key, Val value) {
        if (value != null && !value.isNull()) {
            JsonValue js = JsonUtil.toJson(value);
            if (!js.isNull()) {
                put(key, js);
            }
        }
        return this;
    }

    public JsonObject put(String key, Collection<? extends JsonSerializable> v) {
        if (v != null) {
            JsonArray array = JsonArray.create();
            for (JsonSerializable item : v) {
                array.add(item.writeJson());
            }
            putArray(key, array);
        }
        return this;
    }

    public <T> JsonObject put(String key, Collection<T> list, UnaryFunc<T, JsonValue> itemMapping) {
        if (list != null) {
            JsonArray array = JsonArray.create();
            for (T item : list) {
                array.add(itemMapping.run(item));
            }
            putArray(key, array);
        }
        return this;
    }

    public <K, V> JsonObject put(String key,
                                 Map<K, V> map,
                                 UnaryFunc<K, String> keyMapping,
                                 UnaryFunc<V, JsonValue> valueMapping) {

        JsonObject jso = JsonObject.create();
        for (Map.Entry<K, V> item : map.entrySet()) {
            String itemKey = keyMapping.run(item.getKey());
            if (itemKey == null) {
                continue;
            }
            JsonValue itemVal = valueMapping.run(item.getValue());
            if (itemVal != null && !itemVal.isNull()) {
                jso.put(itemKey, itemVal);
            }
        }

        if (jso.size() > 0) {
            put(key, jso);
        }
        return this;
    }

    public JsonObject putArray(String key, JsonArray array) {
        if (array.size() > 0) {
            put(key, array);
        }
        return this;
    }

    public JsonObject putIntegers(String key, Collection<Integer> v) {
        putArray(key, JsonArray.create().addIntegers(v));
        return this;
    }

    public JsonObject putDoubles(String key, Collection<Double> v) {
        putArray(key, JsonArray.create().addDoubles(v));
        return this;
    }

    public JsonObject putStrings(String key, Collection<String> v) {
        putArray(key, JsonArray.create().addStrings(v));
        return this;
    }

    public JsonObject putEnums(String key, Collection<? extends Enum> v) {
        JsonArray array = JsonArray.create();
        for (Enum item : v) {
            if (item != null) {
                array.add(item.name());
            }
        }
        putArray(key, array);
        return this;
    }

    public <T> Map<String, T> toMap(UnaryFunc<JsonValue, T> valueMapping) {
        Map<String, T> map = new HashMap<>();
        for (String key : getKeys()) {
            map.put(key, valueMapping.run(get(key)));
        }
        return map;
    }

    public <T extends JsonSerializable> T toSerializable(T instance) {
        instance.readJson(this);
        return instance;
    }

}
