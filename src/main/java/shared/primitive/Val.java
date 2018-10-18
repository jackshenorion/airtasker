package shared.primitive;

import java.util.List;

public abstract class Val {

    /**
     * Compare with another value. NOTE: this method doesn't work with List.
     */
    public abstract int comparesTo(Val other);

    public abstract boolean isEqualTo(Val other);

    public boolean isGreaterThan(Val other) {
        if (other.isList()) {
            return comparesTo(Vals.max(other.asList())) > 0;
        } else {
            return comparesTo(other) > 0;
        }
    }

    public boolean isGreaterThanOrEqualTo(Val other) {
        if (other.isList()) {
            return comparesTo(Vals.max(other.asList())) >= 0;
        } else {
            return comparesTo(other) >= 0;
        }
    }

    public boolean isLessThan(Val other) {
        if (other.isList()) {
            return comparesTo(Vals.min(other.asList())) < 0;
        } else {
            return comparesTo(other) < 0;
        }
    }

    public boolean isLessThanOrEqualTo(Val other) {
        if (other.isList()) {
            return comparesTo(Vals.min(other.asList())) <= 0;
        } else {
            return comparesTo(other) <= 0;
        }
    }

    public boolean isBool() { return false; }

    public boolean isNumber() { return false; }

    public boolean isInt() { return false; }

    public boolean isDouble() { return false; }

    public boolean isText() { return false; }

    public boolean isObject() { return false; }

    public boolean isList() { return false; }

    public boolean isNull() { return false; }

    public boolean isEmpty() { return false; }

    public Val divide(Val other) { return IntVal.ZERO; }

    public Val minus(Val other) { return IntVal.ZERO.minus(other); }

    public Val mod(Val other) { return IntVal.ZERO; }

    public Val multiply(Val other) { return IntVal.ZERO; }

    public Val plus(Val other) { return IntVal.ZERO.plus(other); }

    public boolean asBool() { return false; }

    public Boolean asBoolOrNull() { return null; }

    public double asDouble() { return 0; }

    public double asDouble(double returnIfNull) { return returnIfNull; }

    public Double asDoubleOrNull() { return null; }

    public int asInt() { return 0; }

    public int asInt(int returnIfNull) { return returnIfNull; }

    public Integer asIntOrNull() { return null; }

    public String asText() { return "";}

    public List<Val> asList() { return null; }

    public ValList asListVal() {
        return null;
    }

    /**
     * Cast this value to an integer, if it is a number. Otherwise NullVal will be returned.
     */
    public Val toInt() {
        return NullVal.Instance;
    }

    /**
     * Cast this value to a decimal, if it is a number. Otherwise NullVal will be returned.
     */
    public Val toDouble() {
        return NullVal.Instance;
    }

    public Object toObject() { return null; }

    /**
     * Convert this value to string format if possible. Note: ObjectVal returns empty string.
     */
    public abstract String toText();

    /**
     * This method is only meant to be called by HashMap and JUnit. You should call equalsTo() instead which runs faster.
     */
    @Deprecated
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (obj instanceof Val) {
            return isEqualTo((Val) obj);
        }
        return false;
    }

    @Override
    public String toString() {
        return "DEBUG: Val=" + toObject();
    }

}
