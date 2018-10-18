package shared.collection;

public class Pointer<T> {

    T target;

    public Pointer() {
    }

    public Pointer(T target) {
        this.target = target;
    }

    public boolean isPresent() {
        return target != null;
    }

    public T get() {
        return target;
    }

    public Pointer<T> set(T target) {
        this.target = target;
        return this;
    }
}
