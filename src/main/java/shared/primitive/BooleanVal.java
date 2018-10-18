package shared.primitive;

/**
 * Created by   on 17/03/2015.
 */
public final class BooleanVal extends Val {

    public static BooleanVal True = new BooleanVal(true);
    public static BooleanVal False = new BooleanVal(false);

    private boolean val;

    private BooleanVal(boolean val) {
        this.val = val;
    }

    private BooleanVal() {
    }

    @Override
    public int comparesTo(Val other) {
        return other.isBool() ? Boolean.compare(val, other.asBool()) : -1;
    }

    @Override
    public boolean isEqualTo(Val other) {
        return  other.isList() ? other.asListVal().isEqualTo(this) :
                this == other;
    }

    @Override
    public boolean asBool() {
        return val;
    }

    @Override
    public Boolean asBoolOrNull() {
        return val;
    }

    @Override
    public boolean isBool() {
        return true;
    }

    @Override
    public String toText() {
        return Boolean.toString(val);
    }

    @Override
    public Object toObject() {
        return val;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

    @Override
    public int hashCode() {
        return (val ? 1 : 0);
    }
}
