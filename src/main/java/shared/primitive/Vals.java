package shared.primitive;

import util.shared.delegate.BiFunc;
import util.shared.delegate.UnaryFunc;
import util.shared.linq.Linq;
import util.shared.math.MathUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Vals {

    /**
     * Return
     *    - NullVal if the argument is null, NullVal or empty list, OR
     *    - the 1st value if the argument is a list and contains one value, OR
     *    - the argument itself if the argument is a list and contains more than one value.
     */
    public static Val unpackList(Val maybeList) {
        if (maybeList == null) {
            return ofNull();
        }
        if (maybeList.isList()) {
            List<Val> items = maybeList.asList();
            if (items.isEmpty()) {
                return ofNull();
            }
            if (items.size() == 1) {
                return items.get(0);
            }
        }
        return maybeList;
    }

    public static boolean isEmpty(Val val) {
        return val == null || val.isEmpty();
    }

    public static Val of(boolean b) {
        return b ? BooleanVal.True : BooleanVal.False;
    }

    public static Val of(Boolean b) {
        return b != null ? of(b.booleanValue()) : NullVal.Instance;
    }

    public static Val of(int i) {
        return i == 0 ? IntVal.ZERO : new IntVal(i);
    }

    public static Val of(Integer i) {
        return i != null ? of(i.intValue()) : NullVal.Instance;
    }

    public static Val of(double d) {
        return MathUtil.isFinite(d) ? new DoubleVal(d) : Vals.ofNull();
    }

    public static Val of(Double d) {
        return d != null ? of(d.doubleValue()) : NullVal.Instance;
    }

    public static Val of(String s) {
        return s != null ? new TextVal(s) : NullVal.Instance;
    }

    @Deprecated
    public static Val of(Val c) {
        return c != null ? c : NullVal.Instance;
    }

    public static Val of(Object c) {
        return c != null ? new ObjectVal(c) : NullVal.Instance;
    }

    public static Val ofList(Val v) {
        return new ValList(Collections.singletonList(v));
    }

    public static Val ofList(Val... v) {
        return new ValList(Arrays.asList(v));
    }

    public static Val ofList(List<Val> list) {
        return list != null ? new ValList(list) : NullVal.Instance;
    }

    public static Val ofList(List<Val> list, UnaryFunc<Val, Val> mapping) {
        int size = list.size();
        if (size == 0) {
            return ValList.EMPTY;
        }
        List<Val> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            result.add(mapping.run(list.get(i)));
        }
        return Vals.ofList(result);
    }

    public static Val ofListBoolean(boolean ... value) {
        return new ValList(Arrays.asList(wrap(value)));
    }

    public static Val ofListDouble(List<Double> list) {
        return list != null ? new ValList(Linq.wrap(list).select(Vals::of).toList()) : NullVal.Instance;
    }

    public static Val ofListDouble(double ... value) {
        return new ValList(Arrays.asList(wrap(value)));
    }

    public static Val ofListInt(List<Integer> list) {
        return list != null ? new ValList(Linq.wrap(list).select(Vals::of).toList()) : NullVal.Instance;
    }

    public static Val ofListInt(int value) {
        return new ValList(Collections.singletonList(of(value)));
    }

    public static Val ofListInt(int ... value) {
        return new ValList(Arrays.asList(wrap(value)));
    }

    public static Val ofListString(String... list) {
        return ofListString(Arrays.asList(list));
    }

    public static Val ofListString(List<String> list) {
        return list != null ? new ValList(Linq.wrap(list).select(Vals::of).toList()) : NullVal.Instance;
    }

    public static Val ofNull() { return NullVal.Instance; }

    public static Val ofVoid() { return VoidVal.Instance; }

    public static Val divideBy100(Val value) {
        if (value.isNull()) {
            return value;
        }
        if (value.isList()) {
            return Vals.ofList(Linq.wrap(value.asList()).select(Vals::divideBy100).toList());
        }
        return value.divide(of(100d));
    }

    public static Val multiplyBy100(Val value) {
        if (value.isNull()) {
            return value;
        }
        if (value.isList()) {
            return Vals.ofList(Linq.wrap(value.asList()).select(Vals::multiplyBy100).toList());
        }
        return value.multiply(of(100d));
    }

    public static Val max(List<Val> vals) {
        int size = vals.size();
        if (size == 0) {
            return ofNull();
        }
        if (size == 1) {
            return vals.get(0);
        }
        return Collections.max(vals, (v1, v2) -> v1.comparesTo(v2));
    }

    public static Val min(List<Val> vals) {
        int size = vals.size();
        if (size == 0) {
            return ofNull();
        }
        if (size == 1) {
            return vals.get(0);
        }
        return Collections.min(vals, (v1, v2) -> v1.comparesTo(v2));
    }

    public static Val operate(Val operand1, List<Val> operand2, BiFunc<Val, Val, Val> op) {
        int list2Size = operand2.size();
        if (list2Size == 0) {
            return Vals.ofList(op.run(operand1, Vals.ofNull()));
        }
        if (list2Size == 1) {
            return Vals.ofList(op.run(operand1, operand2.get(0)));
        }
        Val[] result = new Val[list2Size];
        for (int i = 0; i < list2Size; i++) {
            result[i] = op.run(operand1, operand2.get(i));
        }
        return Vals.ofList(result);
    }

    public static Val operate(List<Val> operand1, Val operand2, BiFunc<Val, Val, Val> op) {
        int list1Size = operand1.size();
        if (list1Size == 0) {
            return Vals.ofList(op.run(Vals.ofNull(), operand2));
        }
        if (list1Size == 1) {
            return Vals.ofList(op.run(operand1.get(0), operand2));
        }
        Val[] result = new Val[list1Size];
        for (int i = 0; i < list1Size; i++) {
            result[i] = op.run(operand1.get(i), operand2);
        }
        return Vals.ofList(result);
    }

    public static Val operate(List<Val> list1, List<Val> list2, BiFunc<Val, Val, Val> op) {
        int list1Size = list1.size();
        int list2Size = list2.size();

        int maxSize = Math.max(list1Size, list2Size);
        if (maxSize == 0) {
            return ValList.EMPTY;
        }
        if (maxSize == 1) {
            return Vals.ofList(op.run(
                list1Size == 0 ? Vals.ofNull() : list1.get(0),
                list2Size == 0 ? Vals.ofNull() : list2.get(0)
            ));
        }

        List<Val> result = new ArrayList<>(maxSize);
        for (int i = 0; i < maxSize; i++) {
            Val v1 = i >= list1.size() ? Vals.ofNull() : list1.get(i);
            Val v2 = i >= list2Size ? Vals.ofNull() : list2.get(i);
            result.add(op.run(v1, v2));
        }
        return Vals.ofList(result);
    }

    public static Val probe(Object o) {
        if (o == null) return NullVal.Instance;

        Class type = o.getClass();
        if (type == Boolean.class) {
            return of(((Boolean) o).booleanValue());
        }
        if (type == Integer.class) {
            return of(((Integer) o).intValue());
        }
        if (type == Double.class) {
            return of(((Double) o).doubleValue());
        }
        if (type == String.class) {
            return of((String) o);
        }
        if (o instanceof Val) {
            return (Val) o;
        }
        return new ObjectVal(o);
    }

    /**
     * Return the value parameter if it is a positve number, return Vals.ofNull() otherwise.
     */
    public static Val toPositive(Val val) {
        return val.asInt() > 0 ? val : ofNull();
    }

    public static Val[] wrap(boolean[] value) {
        Val[] vals = new Val[value.length];
        for (int i = 0; i < value.length; i++) {
            vals[i] = Vals.of(value[i]);
        }
        return vals;
    }

    public static Val[] wrap(int[] value) {
        Val[] vals = new Val[value.length];
        for (int i = 0; i < value.length; i++) {
            vals[i] = Vals.of(value[i]);
        }
        return vals;
    }

    public static Val[] wrap(double[] value) {
        Val[] vals = new Val[value.length];
        for (int i = 0; i < value.length; i++) {
            vals[i] = Vals.of(value[i]);
        }
        return vals;
    }

    static Val divide(int n1, double n2) {
        return n2 == 0? DoubleVal.ZERO : new DoubleVal(n1 / n2);
    }
    static Val divide(double n1, double n2) {
        return n2 == 0? DoubleVal.ZERO : new DoubleVal(n1 / n2);
    }

}
