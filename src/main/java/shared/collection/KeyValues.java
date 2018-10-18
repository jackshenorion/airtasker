package shared.collection;

import util.shared.ObjectUtil;
import util.shared.date.ISO8601;
import util.shared.json.JsonObject;
import util.shared.json.JsonSerializable;
import util.shared.json.JsonValue;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class KeyValues implements JsonSerializable {
	
	private HashMap<String, String> index = new HashMap<>();

    public KeyValues() {
    }

    public void clear() {
    	index.clear();
    }

    public String get(String key) {
        return key != null ? index.get(key) : null;
    }

    public Boolean getBool(String key) {
        String value = get(key);
        return value == null ? null : ObjectUtil.toBoolean(value, null);
    }

    public boolean getBool(String key, boolean returnIfNull) {
        String value = get(key);
        return value == null ? returnIfNull : ObjectUtil.toBool(value, returnIfNull);
    }

    public Date getDate(String key) {
        return getDate(key, null);
    }

    public Date getDate(String key, Date returnIfNull) {
        try {
            String value = get(key);
            return value == null ? returnIfNull : ISO8601.parseDate(value);
        }
        catch(Exception e) {
            return returnIfNull;
        }
    }

    public Integer getInt(String key) {
    	String value = get(key);
        return value == null ? null : ObjectUtil.toInt(value, null);
    }

    public int getInt(String key, int returnIfNull) {
        String value = get(key);
        return value == null ? returnIfNull : ObjectUtil.toInt(value, returnIfNull);
    }

    public Double getDouble(String key) {
        String value = get(key);
        return value == null ? null : ObjectUtil.toDouble(value, null);
    }

    public double getDouble(String key, double returnIfNull) {
        String value = get(key);
        return value == null ? returnIfNull : ObjectUtil.toDouble(value, returnIfNull);
    }

    public void put(String key, boolean value) {
        index.put(key, Boolean.toString(value));
    }

    public void put(String key, Boolean value) {
        put(key, value != null ? value.toString() : null);
    }

    public void put(String key, int value) {
        index.put(key, Integer.toString(value));
    }

    public void put(String key, Integer value) {
        put(key, value != null ? value.toString() : null);
    }

    public void put(String key, double value) {
        index.put(key, Double.toString(value));
    }

    public void put(String key, Double value) {
        put(key, value != null ? value.toString() : null);
    }

    public void put(String key, Date value) {
        if (value == null) {
            remove(key);
        }
        else {
            index.put(key, ISO8601.formatDate(value));
        }
    }

    public void put(String key, String value) {
        if (value == null) {
            remove(key);
        }
        else {
            index.put(key, value);
        }
    }

    public void put(Map<String, String> items) {
        index.putAll(items);
    }

    public void put(KeyValues items) {
        index.putAll(items.index);
    }

    public void remove(String key) {
    	index.remove(key);
    }

    public int size() {
    	return index.size();
    }

    @Override
    public void readJson(JsonValue json) {
        JsonObject jso = json.asObject();
        if (jso == null) return;

        clear();
        for (String key : jso.getKeys()) {
            put(key, jso.getString(key));
        }
    }

    @Override
    public JsonValue writeJson() {
        JsonObject jso = JsonObject.create();
        for (Map.Entry<String, String> entry : index.entrySet()) {
            jso.put(entry.getKey(), entry.getValue());
        }
        return jso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KeyValues keyValues = (KeyValues) o;
        return index.equals(keyValues.index);
    }

    @Override
    public int hashCode() {
        return index.hashCode();
    }
}
