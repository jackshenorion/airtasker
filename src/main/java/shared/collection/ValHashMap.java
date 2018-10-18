package shared.collection;

import util.shared.primitive.Val;
import util.shared.primitive.Vals;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A specialised HashMap with key of custom type and value of Val type. When searching for a value from the map, it returns
 * Vals.ofNull() if the value is not found.
 */
public final class ValHashMap<T> implements Map<T, Val> {

    private HashMap<T, Val> map = new HashMap<>();

    public ValHashMap() {
    }

    public ValHashMap(Map<? extends T, ? extends Val> copyFrom) {
        putAll(copyFrom);
    }

    @Override
    public void clear() {
        map.clear();
    }

    protected Object clone() {
        return new ValHashMap<>(this);
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public Set<Entry<T, Val>> entrySet() {
        return map.entrySet();
    }

    @Override
    public Val get(Object key) {
        Val val = map.get(key);
        return toNotNull(val);
    }

    @Override
    public Val getOrDefault(Object key, Val defaultValue) {
        Val val = get(key);
        return val.isNull() ? defaultValue : val;
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public Set<T> keySet() {
        return map.keySet();
    }

    @Override
    public Val put(T key, Val value) {
        if (value == null || value.isNull()) {
            return remove(key);
        }
        return toNotNull(map.put(key, value));
    }

    @Override
    public void putAll(Map<? extends T, ? extends Val> m) {
        for (Entry<? extends T, ? extends Val> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public Val putIfAbsent(T key, Val value) {
        Val current = get(key);
        if (!current.isNull()) {
            return current;
        }
        return put(key, value);
    }

    @Override
    public Val remove(Object key) {
        return toNotNull(map.remove(key));
    }

    @Override
    public boolean remove(Object key, Object value) {
        return map.remove(key, value);
    }

    @Override
    public boolean replace(T key, Val oldValue, Val newValue) {
        return map.replace(key, oldValue, newValue);
    }

    @Override
    public Val replace(T key, Val value) {
        return map.replace(key, value);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public Collection<Val> values() {
        return map.values();
    }

    private Val toNotNull(Val val) {
        return val != null ? val : Vals.ofNull();
    }
}
