package shared.json;

import java.util.Comparator;

public class JsonComparators {

    public static class Number implements Comparator<JsonValue> {

        public int compare(JsonValue x, JsonValue y) {
            if (x == null && y == null) return 0;
            if (x == null) return -1;
            if (y == null) return 1;

            JsonNumber numX = x.asNumber();
            JsonNumber numY = y.asNumber();
            if (numX == null || numY == null) return 0;

            return Double.compare(numX.getValue(), numY.getValue());
        }
    }
}
