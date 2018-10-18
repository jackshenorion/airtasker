package shared.primitive;

import util.shared.ObjectUtil;

/**
 * Created by   on 17/03/2015.
 */
public final class IntVal extends Val {

    public static final Val ZERO = new IntVal(0);
    public static final Val ZERO_ARRAY = Vals.ofListInt(0);

    private int val;

    public IntVal(int val) {
        this.val = val;
    }

    @Override
    public int comparesTo(Val other) {
        return  other.isInt() ? Integer.compare(val, other.asInt()) :
                ObjectUtil.compares(val, other.asDouble(), DoubleVal.DELTA);
    }

    @Override
    public boolean isEqualTo(Val other) {
        return  other.isInt() ? val == other.asInt() :
                other.isDouble() ? ObjectUtil.equals(val, other.asDouble(), DoubleVal.DELTA) :
                other.isList() ? other.asListVal().isEqualTo(this) :
                false;
    }

    @Override
    public Val divide(Val other) {
        return  other.isInt() ? Vals.divide(val, other.asDouble()) :
                other.isDouble() ? Vals.divide(val, other.asDouble()) :
                other.isList() ? Vals.operate(this, other.asList(), Val::divide) :
                other.isObject() && other.toObject() instanceof HasValOperations ? ((HasValOperations) other.toObject()).isDividedBy(this) :
                ZERO;
    }

    @Override
    public Val minus(Val other) {
        return  other.isInt() ? Vals.of(val - other.asInt()) :
                other.isDouble() ? Vals.of(val - other.asDouble()) :
                other.isList() ? Vals.operate(this, other.asList(), Val::minus) :
                other.isNull() ? this :
                other.isObject() && other.toObject() instanceof HasValOperations ? ((HasValOperations) other.toObject()).isSubtractedBy(this) :
                this;
    }

    @Override
    public Val mod(Val other) {
        return  other.isInt() ? Vals.of(val % other.asInt()) :
                other.isDouble() ? Vals.of(val % other.asDouble()) :
                other.isList() ? Vals.operate(this, other.asList(), Val::mod) :
                ZERO;
    }

    @Override
    public Val multiply(Val other) {
        return  other.isInt() ? Vals.of(val * other.asInt()) :
                other.isDouble() ? Vals.of(val * other.asDouble()) :
                other.isList() ? Vals.operate(this, other.asList(), Val::multiply) :
                other.isObject() && other.toObject() instanceof HasValOperations ? ((HasValOperations) other.toObject()).isMultipliedBy(this) :
                ZERO;
    }

    @Override
    public Val plus(Val other) {
        return  other.isInt() ? Vals.of(val + other.asInt()) :
                other.isDouble() ? Vals.of(val + other.asDouble()) :
                other.isList() ? Vals.operate(this, other.asList(), Val::plus) :
                other.isObject() && other.toObject() instanceof HasValOperations ? ((HasValOperations) other.toObject()).isAddedBy(this) :
                this;
    }

    @Override
    public boolean isNumber() {
        return true;
    }

    @Override
    public boolean isInt() {
        return true;
    }

    @Override
    public boolean isDouble() {
        return true;
    }

    @Override
    public int asInt() {
        return val;
    }

    @Override
    public int asInt(int defaultInt) {
        return val;
    }

    @Override
    public Integer asIntOrNull() {
        return val;
    }

    @Override
    public double asDouble() {
        return val;
    }

    @Override
    public Double asDoubleOrNull() {
        return (double) val;
    }

    @Override
    public double asDouble(double returnIfNull) {
        return (double) val;
    }

    @Override
    public Val toInt() {
        return this;
    }

    @Override
    public Val toDouble() {
        return new DoubleVal(val);
    }

    @Override
    public String toText() {
        return Integer.toString(val);
    }

    @Override
    public Object toObject() {
        return val;
    }

    @Override
    public int hashCode() {
        return val;
    }
}
