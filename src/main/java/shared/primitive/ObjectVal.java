package shared.primitive;

import java.util.Objects;

/**
 * Created by   on 17/03/2015.
 */
public final class ObjectVal extends Val {

    private Object obj;
    private Comparable<Object> comparable;
    private boolean comparableChecked;

    public ObjectVal(Object obj) {
        this.obj = obj;
    }

    @Override
    public boolean isEqualTo(Val other) {
        return other.isList() ? other.asListVal().isEqualTo(this) :
                other.isObject() && Objects.equals(other.toObject(), obj);
    }

    @Override
    public int comparesTo(Val other) {
        if (!comparableChecked) {
            comparable = obj instanceof Comparable ? (Comparable<Object>) obj : null;
            comparableChecked = true;
        }
        if (comparable != null) {
            return comparable.compareTo(other.toObject());
        }
        return other.isObject() && other.toObject() == obj ? 0 : -1; // not comparable ...
    }

    @Override
    public Val divide(Val other) {
        if (obj instanceof HasValOperations) {
            return ((HasValOperations) obj).divide(other);
        }
        return super.divide(other);
    }

    @Override
    public Val minus(Val other) {
        if (obj instanceof HasValOperations) {
            return ((HasValOperations) obj).subtract(other);
        }
        return super.minus(other);
    }

    @Override
    public Val multiply(Val other) {
        if (obj instanceof HasValOperations) {
            return ((HasValOperations) obj).multiply(other);
        }
        return super.multiply(other);
    }

    @Override
    public Val plus(Val other) {
        if (obj instanceof HasValOperations) {
            return ((HasValOperations) obj).add(other);
        }
        return super.plus(other);
    }

    @Override
    public boolean isObject() {
        return true;
    }

    @Override
    public String toText() {
        return obj.toString();
    }

    @Override
    public Object toObject() {
        return obj;
    }

    @Override
    public int hashCode() {
        return obj != null ? obj.hashCode() : 0;
    }
}
