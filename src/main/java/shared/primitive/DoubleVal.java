package shared.primitive;

import util.shared.ObjectUtil;

/**
 * Created by   on 17/03/2015.
 */
public final class DoubleVal extends Val {

    public static final Val ZERO = new DoubleVal(0);
    static final double DELTA = 0.0000001;
    private double val;

    public DoubleVal(double val) {
        this.val = val;
    }

    public DoubleVal() {
    }

    @Override
    public int comparesTo(Val other) {
        return ObjectUtil.compares(val, other.asDouble(), DELTA);
    }

    @Override
    public boolean isEqualTo(Val other) {
        return  other.isDouble() ? ObjectUtil.equals(val, other.asDouble(), DELTA) :
                other.isList() ? other.asListVal().isEqualTo(this) :
                false;
    }

    @Override
    public int asInt() {
        /**
         * Generally a decimal should not be treated as an integer so isInt method returns false.
         * However if this method is called, a meaningful integer should be returned rather than the default value 0.
         */
        return (int) val;
    }

    @Override
    public int asInt(int returnIfNull) {
        return (int) val;
    }

    @Override
    public Integer asIntOrNull() {
        /**
         * Generally a decimal should not be treated as an integer so isInt method returns false.
         * However if this method is called, a meaningful integer should be returned rather than the default value 0.
         */
        return (int) val;
    }

    @Override
    public Val divide(Val other) {
        return  other.isDouble() ? Vals.divide(val, other.asDouble()) :
                other.isList() ? Vals.operate(this, other.asList(), Val::divide) :
                other.isObject() && other.toObject() instanceof HasValOperations ? ((HasValOperations) other.toObject()).isDividedBy(this) :
                ZERO;
    }

    @Override
    public Val minus(Val other) {
        return  other.isDouble() ? Vals.of(val - other.asDouble()) :
                other.isList() ? Vals.operate(this, other.asList(), Val::minus) :
                other.isNull() ? this :
                other.isObject() && other.toObject() instanceof HasValOperations ? ((HasValOperations) other.toObject()).isSubtractedBy(this) :
                this;
    }

    @Override
    public Val mod(Val other) {
        return  other.isDouble() ? Vals.of(val % other.asDouble()) :
                other.isList() ? Vals.operate(this, other.asList(), Val::mod) :
                ZERO;
    }

    @Override
    public Val multiply(Val other) {
        return  other.isDouble() ? Vals.of(val * other.asDouble()) :
                other.isList() ? Vals.operate(this, other.asList(), Val::multiply) :
                other.isObject() && other.toObject() instanceof HasValOperations ? ((HasValOperations) other.toObject()).isMultipliedBy(this) :
                ZERO;
    }

    @Override
    public Val plus(Val other) {
        return  other.isDouble() ? Vals.of(val + other.asDouble()) :
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
        return false;
    }

    @Override
    public boolean isDouble() {
        return true;
    }

    @Override
    public double asDouble() {
        return val;
    }

    @Override
    public Double asDoubleOrNull() {
        return val;
    }

    @Override
    public double asDouble(double returnIfNull) {
        return val;
    }

    @Override
    public Val toInt() {
        return new IntVal((int) val);
    }

    @Override
    public Val toDouble() {
        return this;
    }

    @Override
    public String toText() {
        return Double.toString(val);
    }

    @Override
    public Object toObject() {
        return val;
    }

    @Override
    public int hashCode() {
        return (int)(val * 100);
    }
}
