package shared.primitive.mutable;

/**
 * MutableInt is a light alternative to java.util.concurrent.atomic.AtomicInteger which may not be accessible outside of
 * server environment.
 */
public class MutableInt {

    private volatile int value;

    public MutableInt(int value) {
        this.value = value;
    }

    public int get() {
        return value;
    }

    public int decrementAndGet() {
        return --value;
    }

    public int getAndDecrement() {
        return value--;
    }

    public int getAndIncrement() {
        return value++;
    }

    public int incrementAndGet() {
        return ++value;
    }

    public MutableInt set(int target) {
        this.value = target;
        return this;
    }
}
