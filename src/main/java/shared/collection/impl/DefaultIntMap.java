package shared.collection.impl;

import util.shared.collection.IntMap;
import util.shared.delegate.Func;
import util.shared.delegate.UnaryAction;

import java.util.Collection;
import java.util.HashMap;

/**
 * This is the default implementation of {@link IntMap} based on {@link HashMap}
 *
 * You should not use this class directly.
 */
public class DefaultIntMap<T> extends IntMap<T> {

    private HashMap<Integer, T> map = new HashMap<>();

    public DefaultIntMap() {
    }

    public DefaultIntMap(DefaultIntMap<T> copyFrom) {
        map = new HashMap<>(copyFrom.map);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public IntMap<T> clone() {
        return new DefaultIntMap<>(this);
    }

    @Override
    public T get(int key) {
        return map.get(key);
    }

    @Override
    public void put(int key, T value) {
        map.put(key, value);
    }

    @Override
    public T remove(int index) {
        return map.remove(index);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public int[] keysAsInt() {
        return DefaultIntSet.toInts(map.keySet());
    }

    @Override
    public Collection<T> values() {
        return map.values();
    }

    @Override
    public void forEachValue(UnaryAction<T> action) {
        for (T value: values()) {
            action.run(value);
        }
    }

    public static Func<IntMap<?>> Factory = DefaultIntMap::new;
}
