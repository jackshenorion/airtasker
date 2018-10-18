package shared.diff;

import util.shared.collection.IsInt;

public enum ChangeType implements IsInt {

    Updated() { @Override public int toInt() { return 0; }},
    Created() { @Override public int toInt() { return 1; }},
    Removed() { @Override public int toInt() { return 2; }},
    Moved()   { @Override public int toInt() { return 3; }};

    public final static ChangeType getByInt(int intValue) {
        for (ChangeType item : values()) {
            if (item.toInt() == intValue) {
                return item;
            }
        }

        return null;
    }

}
