package shared.binding;

public final class PropertyBinding<T, P> {

    PropertyGetter<T, P> getter;
    PropertySetter<T, P> setter;

    public PropertyBinding() {
    }

    public PropertyBinding(PropertyGetter<T, P> getter, PropertySetter<T, P> setter) {
        getter(getter).setter(setter);
    }

    public PropertyGetter<T, P> getter() {
        return getter;
    }

    public PropertyBinding<T, P> getter(PropertyGetter<T, P> getter) {
        this.getter = getter;
        return this;
    }

    public PropertySetter<T, P> setter() {
        return setter;
    }

    public PropertyBinding<T, P> setter(PropertySetter<T, P> setter) {
        this.setter = setter;
        return this;
    }
}
