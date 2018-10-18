package shared.collection;

import util.shared.delegate.Func;

/**
 * Created by   on 20/02/2015.
 */
public final class Lazy<T> {

    private Func<T> creator;
    private T instance;

    private Lazy(Func<T> creator) {
        this.creator = creator;
    }

    public synchronized boolean isCreated() {
        return instance != null;
    }

    public synchronized T get() {
        return instance != null ? instance : (instance = creator.run());
    }

    public synchronized void release() {
        instance = null;
    }

    public static <T> Lazy<T> of(Func<T> creator) {
        return new Lazy<>(creator);
    }

}
