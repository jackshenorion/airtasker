package shared.collection;

import util.shared.delegate.UnaryFunc;

import java.util.HashMap;

public class StringEnumMap<T extends Enum<T>> {

    private HashMap<String, T> map;

    public StringEnumMap(T[] enumItems) {
        map = new HashMap<>(enumItems.length * 3);
        for (T enumItem : enumItems) {
            map.put(enumItem.name(), enumItem);
        }
    }

    public StringEnumMap(T[] enumItems, UnaryFunc<T, String> keySupplier) {
        map = new HashMap<>(enumItems.length * 3);
        for (T enumItem : enumItems) {
            map.put(keySupplier.run(enumItem), enumItem);
        }
    }

    public T get(String name, T returnIfNull) {
        T instance = map.get(name);
        return instance != null ? instance : returnIfNull;
    }
}
