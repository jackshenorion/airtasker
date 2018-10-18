package shared;

import java.util.HashMap;
import java.util.Map;

public class Enums {

    public static <E extends Enum<E>> int ordinal(E item) {
        return item == null ? -1 : item.ordinal();
    }

    public static <E extends Enum<E>> E valueOf(Class<E> clz, String name) {
        return valueOf(clz, name, null);
    }

    public static <E extends Enum<E>> E valueOf(Class<E> clz, String name, E returnIfNull) {
        if (Strings.isNullOrEmpty(name)) {
            return returnIfNull;
        }
        try {
            return Enum.valueOf(clz, name);
        }
        catch (IllegalArgumentException e) {
            return returnIfNull;
        }
    }

    public static <E extends Enum<E>> String name(E item, String defaultName) {
        if (item != null) {
            return item.name();
        } else {
            return defaultName;
        }
    }

    public static <E extends Enum<E>> Map<String, E> toMap(E[] enumItems) {
        Map<String, E> map = new HashMap<>(enumItems.length * 3);
        for (E enumItem : enumItems) {
            map.put(enumItem.name(), enumItem);
        }
        return map;
    }


}
