package shared.json;


import util.shared.Enums;
import util.shared.date.ISO8601;
import util.shared.delegate.Func;
import util.shared.delegate.UnaryFunc;
import util.shared.primitive.Val;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public abstract class JsonArray extends JsonValue {
	
	public abstract void add(JsonValue value); 
	
	public abstract JsonValue get(int index);
	
	public abstract int size();

	@Override
	public JsonArray asArray() {
		return this;
	}

    @Override
    public boolean isArray() { return true; }

    public static JsonArray create() {
		return JsonParser.impl.createArray();
	}


    public JsonArray add(boolean v) {
        add(JsonBoolean.get(v));
        return this;
    }

    public JsonArray add(Boolean v) {
        add(v != null ? JsonBoolean.get(v) : JsonNull.Instance);
        return this;
    }

    public JsonArray add(Date v) {
        add(v != null ? new JsonString(ISO8601.formatDate(v)) : JsonNull.Instance);
        return this;
    }

    public JsonArray add(int v) {
        add(new JsonNumber(v));
        return this;
    }

    public JsonArray add(Integer v) {
        add(v != null ? new JsonNumber(v) : JsonNull.Instance);
        return this;
    }

    public JsonArray add(double v) {
        add(new JsonNumber(v));
        return this;
    }

    public JsonArray add(Double v) {
        add(v != null ? new JsonNumber(v) : JsonNull.Instance);
        return this;
    }

    public JsonArray add(String v) {
        add(v != null ? new JsonString(v) : JsonNull.Instance);
        return this;
    }

    public JsonArray add(Val v) {
        if (v == null || v.isNull()) {
            add(JsonNull.Instance);
        }
        else if (v.isList()) {
            addVals(v.asList());
        }
        else {
            add(JsonUtil.toJson(v));
        }
        return this;
    }

    public JsonArray add(JsonSerializable v) {
        add(v != null ? v.writeJson() : JsonNull.Instance);
        return this;
    }

    public JsonArray add(Collection<? extends JsonSerializable> v) {
        for (JsonSerializable item : v) {
            add(item.writeJson());
        }
        return this;
    }

    public <T> JsonArray add(Collection<T> v, UnaryFunc<T, JsonValue> converter) {
        for (T item : v) {
            add(converter.run(item));
        }
        return this;
    }

    public JsonArray addAll(Collection<JsonValue> v) {
        for (JsonValue item : v) {
            add(item);
        }
        return this;
    }

    public JsonArray addIntegers(Collection<Integer> v) {
        for (Integer item : v) {
            add(new JsonNumber(item));
        }
        return this;
    }

    public JsonArray addDoubles(Collection<Double> v) {
        for (Double item : v) {
            add(new JsonNumber(item.intValue()));
        }
        return this;
    }

    public JsonArray addStrings(Collection<String> v) {
        for (String item : v) {
            add(new JsonString(item));
        }
        return this;
    }

    public JsonArray addVals(Collection<Val> v) {
        for (Val val : v) {
            add(JsonUtil.toJson(val));
        }
        return this;
    }

    public Boolean getBoolean(int key) {
        JsonBoolean v = get(key).asBoolean();
        return v != null ? v.getValue() : null;
    }

    public boolean getBoolean(int key, boolean returnIfNull) {
        JsonBoolean v = get(key).asBoolean();
        return v != null ? v.getValue() : returnIfNull;
    }

    public Date getDate(int key) {
        return getDate(key, null);
    }

    public Date getDate(int key, Date returnIfNull) {
        Date date = ISO8601.parseDate(getString(key));
        return date != null ? date : returnIfNull;
    }

    public Double getDouble(int key) {
        JsonNumber v = get(key).asNumber();
        return v != null ? v.getValue() : null;
    }

    public double getDouble(int key, double returnIfNull) {
        JsonNumber v = get(key).asNumber();
        return v != null ? v.getValue() : returnIfNull;
    }

    public <T extends Enum<T>> T getEnum(int key, Class<T> enumType) {
        JsonString v = get(key).asString();
        return v != null ? Enums.valueOf(enumType, v.getValue(), null) : null;
    }

    public Integer getInt(int key) {
        JsonNumber v = get(key).asNumber();
        return v != null ? (int) v.getValue() : null;
    }

    public int getInt(int key, int returnIfNull) {
        JsonNumber v = get(key).asNumber();
        return v != null ? (int) v.getValue() : returnIfNull;
    }

    public String getString(int key) {
        return getString(key, "");
    }

    public String getString(int key, String returnIfNull) {
        JsonString v = get(key).asString();
        return v != null ? v.getValue().trim() : returnIfNull;
    }

    public <T extends Enum<T>> List<T> getEnums(Class<T> enumType) {
        List<T> result = new ArrayList<>();
        for (int i = 0, k = size(); i < k; i++) {
            T v = getEnum(i, enumType);
            if (v != null) {
                result.add(v);
            }
        }
        return result;
    }

    public <T extends JsonSerializable> List<T> getList(Func<T> itemCreator) {
        List<T> result = new ArrayList<>();

        for (int i = 0, k = size(); i < k; i++) {
            T instance = itemCreator.run();
            instance.readJson(get(i));
            result.add(instance);
        }
        return result;
    }

    public <T> List<T> getList(UnaryFunc<JsonValue, T> itemConverter) {
        int size = size();
        List<T> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            T instance = itemConverter.run(get(i)); // can be null
            result.add(instance);
        }
        return result;
    }

    public <T> List<T> getListIfPresent(UnaryFunc<JsonValue, T> itemConverter) {
        int size = size();
        List<T> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            T instance = itemConverter.run(get(i));
            if (instance != null) {
                result.add(instance);
            }
        }
        return result;
    }

    public List<String> getStrings() {
        List<String> result = new ArrayList<>();

        for (int i = 0, k = size(); i < k; i++) {
            JsonString item = get(i).asString();
            if (item != null) {
                result.add(item.getValue());
            }
        }
        return result;
    }

    public List<Integer> getIntegers() {
        List<Integer> result = new ArrayList<>();

        for (int i = 0, k = size(); i < k; i++) {
            JsonNumber item = get(i).asNumber();
            if (item != null) {
                result.add((int) item.getValue());
            }
        }
        return result;
    }

    public List<Double> getDoubles() {
        List<Double> result = new ArrayList<>();

        for (int i = 0, k = size(); i < k; i++) {
            JsonNumber item = get(i).asNumber();
            if (item != null) {
                result.add(item.getValue());
            }
        }
        return result;
    }
}
