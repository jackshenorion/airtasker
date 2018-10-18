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
public final class NullVal extends Val {

    static final Val Instance = new NullVal();

    private NullVal() {
    }

    @Override
    public int comparesTo(Val other) {
        return other.isNull() ? 0 : ObjectUtil.compares(0, other.asDouble(), DoubleVal.DELTA);
    }

    @Override
    public boolean isEqualTo(Val other) {
        return other.isList() ? other.asListVal().isEqualTo(this) :
                other.isNull();
    }

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
        return -1;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this;
    }
}
