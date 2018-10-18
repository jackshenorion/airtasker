package shared.binding;


import util.shared.primitive.Val;
import util.shared.primitive.Vals;

public final class ValBindings {

    public static Binding<Val> ofBool(Binding<Boolean> binding) {
        return new Binding<>(
            () -> Vals.of(binding.getter().get()),
            newValue -> binding.setter().set(newValue.asBoolOrNull())
        );
    }

    public static Binding<Boolean> toBool(Binding<Val> binding) {
        return new Binding<>(
            () -> binding.getter().get().asBoolOrNull(),
            newValue -> binding.setter().set(Vals.of(newValue))
        );
    };

    public static Binding<Val> ofInt(Binding<Integer> binding) {
        return new Binding<>(
            () -> Vals.of(binding.getter().get()),
            newValue -> binding.setter().set(newValue.asIntOrNull())
        );
    }

    public static Binding<Integer> toInt(Binding<Val> binding) {
        return new Binding<>(
            () -> binding.getter().get().asIntOrNull(),
            newValue -> binding.setter().set(Vals.of(newValue))
        );
    }

    public static Binding<Double> toDouble(Binding<Val> binding) {
        return new Binding<>(
            () -> binding.getter().get().asDoubleOrNull(),
            newValue -> binding.setter().set(Vals.of(newValue))
        );
    }

}
