package shared.collection;

import java.util.Collection;
import java.util.function.ToIntFunction;

public final class IntMaps {

    public static <T> IntMap<T> from(Collection<T> items, ToIntFunction<T> keyMapping) {
        IntMap<T> map = IntMap.create();
        for (T item : items) {
            map.put(keyMapping.applyAsInt(item), item);
        }
        return map;
    }

}
