package shared.primitive;

import util.shared.ObjectUtil;

/**
 * The difference between VoidVal and NullVal is that NullVal is treated as 0 in calculation whereas VoidVal is incalculable.
 *
 * E.g. NullVal * 8 = 0
 *      NulLVal + 8 = 8
 *      VoidVal * 8 = VoidVal
 *      VoidVal + 8 = VoidVal
 *
 */
public final class VoidVal extends Val {

    static final Val Instance = new VoidVal();

    private VoidVal() {
    }

    @Override
    public int comparesTo(Val other) {
        return other == this ? 0 : ObjectUtil.compares(asDouble(), other.asDouble(), DoubleVal.DELTA);
    }

    @Override
    public boolean isEqualTo(Val other) {
        return other.isList() ? other.asListVal().isEqualTo(this) :
                other.isNull();
    }

    @Override
    public Val divide(Val other) { return VoidVal.Instance; }

    @Override
    public Val minus(Val other) { return VoidVal.Instance; }

    @Override
    public Val mod(Val other) { return VoidVal.Instance; }

    @Override
    public Val multiply(Val other) { return VoidVal.Instance; }

    @Override
    public Val plus(Val other) { return VoidVal.Instance; }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean isNull() {
        return true;
    }

    @Override
    public String toText() {
        return "";
    }

    @Override
    public Object toObject() {
        return null;
    }

    @Override
    public int hashCode() {
        return -2;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this;
    }
}
