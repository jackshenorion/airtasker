package shared.collection;

import util.shared.delegate.Func;

/**
 * This class abstracts the essential functions of a set that stores integers. While
 * its default implementation is based on HashSet, it allows easily switching
 * to a different implementation via {@link #init(Func)}.
 */
public abstract class IntSet {

    public abstract void clear();
    public abstract void add(int value);
    public abstract boolean contains(int value);
    public abstract boolean remove(int value);
    public abstract int size();
    public abstract int[] toArray();

    public static IntSet create() { return factory.run(); }
    public static void init(Func<IntSet> factory) { IntSet.factory = factory; }
    private static Func<IntSet> factory;
}
