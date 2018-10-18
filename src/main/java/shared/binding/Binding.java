package shared.binding;

import util.shared.delegate.UnaryAction;

/**
 * A binding contains a getter and a setter, for reading and writing value, respectively.
 *
 * @param <T>
 */
public final class Binding<T> {

    private static final Setter EMPTY_SETTER = newValue -> {};

    private Getter<T> getter;
    private Setter<T> setter;

    public Binding() {
    }

    public Binding(Getter<T> getter) {
        this(getter, EMPTY_SETTER);
    }

    public Binding(Getter<T> getter, Setter<T> setter) {
        getter(getter).setter(setter);
    }

    public Getter<T> getter() {
        return getter;
    }

    public Binding<T> getter(Getter<T> getter) {
        this.getter = getter;
        return this;
    }

    public Setter<T> setter() {
        return setter;
    }

    public Binding<T> setter(Setter<T> setter) {
        this.setter = setter;
        return this;
    }

    /**
     * In most cases, observation logic can be placed in setter. However in some cases, you may
     * see a need to have a separate / additional observer, then this method comes handy.
     *
     * e.g.
     * <code>
     *     Binding<String> nameBinding = new Binding<String>
     *         .getter(person::getName)
     *         .setter(person::setName)
     *         .observe(newName -> System.out.println("name is changed to : " + newName))
     *     ;
     * </code>
     */
    public Binding<T> observe(UnaryAction<T> observer) {
        assert setter != null : "setting must be set before observer";
        setter = new ObserverSetter<>(setter, observer);
        return this;
    }

    // For decorating a setter with a observer
    private static final class ObserverSetter<T> implements Setter<T> {

        Setter<T> setter;
        UnaryAction<T> observer;

        private ObserverSetter(Setter<T> setter, UnaryAction<T> observer) {
            this.setter = setter;
            this.observer = observer;
        }

        @Override
        public void set(T value) {
            setter.set(value);
            observer.run(value);
        }
    }
}
