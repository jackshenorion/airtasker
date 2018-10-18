package shared.collection;

import util.shared.delegate.Func;
import util.shared.delegate.UnaryAction;

import java.util.Collection;

/**
 * This class abstracts the essential functions of a map that uses integers as keys. While
 * its default implementation is based on HashMap, it allows easily switching
 * to a different implementation via {@link #init(Func)}.
 *
 * @param <T>
 */
public abstract class IntMap<T> {

    public abstract void clear();
    public abstract IntMap<T> clone();
    public abstract T get(int key);
    public abstract void put(int key, T value);
    public abstract T remove(int index);
    public abstract int size();
    public abstract int[] keysAsInt();
    public abstract Collection<T> values();
    public abstract void forEachValue(UnaryAction<T> action);

    public static <T> IntMap<T> create() { return (IntMap<T>) factory.run(); }
    public static void init(Func<IntMap<?>> factory) { IntMap.factory = factory; }
    private static Func<IntMap<?>> factory;

}
