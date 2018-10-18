package shared.collection;

import util.shared.delegate.UnaryFunc;

import java.util.HashMap;

public class IntEnumMap<T extends Enum<T> & IsInt> {

    private HashMap<Integer, T> map;

    public IntEnumMap(T[] enumItems) {
        map = new HashMap<>(enumItems.length * 3);
        for (T enumItem : enumItems) {
            map.put(enumItem.toInt(), enumItem);
        }
    }

    public IntEnumMap(T[] enumItems, UnaryFunc<T, Integer> keySupplier) {
        map = new HashMap<>(enumItems.length * 3);
        for (T enumItem : enumItems) {
            map.put(keySupplier.run(enumItem), enumItem);
        }
    }

    public T get(int intValue, T returnIfNull) {
        T instance = map.get(intValue);
        return instance != null ? instance : returnIfNull;
    }
}
