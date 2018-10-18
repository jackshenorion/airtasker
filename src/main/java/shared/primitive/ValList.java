package shared.primitive;

import util.shared.CollectionUtil;
import util.shared.linq.Linq;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class ValList extends Val implements Iterable<Val> {

    public static final ValList EMPTY = new ValList(Collections.emptyList());

    private List<Val> list;
    private int size;

    public ValList(List<Val> list) {
        this.list = list;
        this.size = list.size();
    }

    @Override
    public Val divide(Val other) {
        if (other.isList()) {
            return Vals.operate(list, other.asList(), Val::divide);
        } else {
            return Vals.operate(list, other, Val::divide);
        }
    }

    @Override
    public Val minus(Val other) {
        if (other.isList()) {
            return Vals.operate(list, other.asList(), Val::minus);
        } else {
            return Vals.operate(list, other, Val::minus);
        }
    }

    @Override
    public Val mod(Val other) {
        if (other.isList()) {
            return Vals.operate(list, other.asList(), Val::mod);
        } else {
            return Vals.operate(list, other, Val::mod);
        }
    }

    @Override
    public Val multiply(Val other) {
        if (other.isList()) {
            return Vals.operate(list, other.asList(), Val::multiply);
        } else {
            return Vals.operate(list, other, Val::multiply);
        }
    }

    @Override
    public Val plus(Val other) {
        if (other.isList()) {
            return Vals.operate(list, other.asList(), Val::plus);
        } else {
            return Vals.operate(list, other, Val::plus);
        }
    }

    public boolean contains(Val val) {
        if (val.isList()) {
            return containsAll(val.asList());
        }
        else {
            for (int i = 0; i < size; i++) {
                if (list.get(i).isEqualTo(val)) {
                    return true;
                }
            }
            return false;
        }
    }

    public boolean containsAll(List<Val> vals) {
        for (int i = 0; i < vals.size(); i++) {
            if (!contains(vals.get(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean containsAny(List<Val> vals) {
        for (int i = 0; i < vals.size(); i++) {
            if (contains(vals.get(i))) {
                return true;
            }
        }
        return false;
    }

    public Val get(int index) {
        return index < 0 || index >= size ? Vals.ofNull() : list.get(index);
    }

    @Override
    public Iterator<Val> iterator() {
        return list.iterator();
    }

    @Override
    public int comparesTo(Val other) {
        // list is not comparable.
        return other == this ? 0 : -1;
    }

    @Override
    public boolean isEqualTo(Val other) {
        if (!other.isList()) {
            if (size == 0) {
                return other.isNull();
            }
            if (size == 1) {
                return list.get(0).isEqualTo(other);
            }
            return false;
        }

        List<Val> otherList = other.asList();
        if (otherList.size() != size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!list.get(i).isEqualTo(otherList.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isGreaterThan(Val other) {
        if (other.isList()) {
            return Vals.min(list).isGreaterThan(Vals.max(other.asList()));
        }
        else {
            return Vals.min(list).isGreaterThan(other);
        }
    }

    @Override
    public boolean isGreaterThanOrEqualTo(Val other) {
        if (other.isList()) {
            boolean greaterThanOrEqualTo = Vals.min(list).isGreaterThanOrEqualTo(Vals.max(other.asList()));
            if (!greaterThanOrEqualTo) {
                greaterThanOrEqualTo = isEqualTo(other);
            }
            return greaterThanOrEqualTo;
        }
        else {
            return Vals.min(list).isGreaterThanOrEqualTo(other);
        }
    }

    @Override
    public boolean isLessThan(Val other) {
        if (other.isList()) {
            return Vals.max(list).isLessThan(Vals.min(other.asList()));
        }
        else {
            return Vals.max(list).isLessThan(other);
        }
    }

    @Override
    public boolean isLessThanOrEqualTo(Val other) {
        if (other.isList()) {
            boolean lessThanOrEqualTo = Vals.max(list).isLessThanOrEqualTo(Vals.min(other.asList()));
            if (!lessThanOrEqualTo) {
                lessThanOrEqualTo = isEqualTo(other);
            }
            return lessThanOrEqualTo;
        }
        else {
            return Vals.max(list).isLessThanOrEqualTo(other);
        }
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean isList() {
        return true;
    }

    @Override
    public List<Val> asList() {
        return list;
    }

    @Override
    public ValList asListVal() {
        return this;
    }

    public int size() {
        return list.size();
    }

    @Override
    public String toText() {
        return "[" + CollectionUtil.join(", ", list, Val::toText, true) + "]";
    }

    @Override
    public Object toObject() {
        return list;
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    public Val[] toArray() {
        Val[] array = new Val[size];
        list.toArray(array);
        return array;
    }

    public List<String> toListString() {
        return Linq.wrap(list).select(v -> v.asText()).toList();
    }

}
