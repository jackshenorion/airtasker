package shared.collection;

import util.shared.delegate.Func;

/**
 * This class abstracts the essential functions of a list that stores integers. While
 * its default implementation is based on ArrayList, it allows easily switching
 * to a different implementation via {@link #init(Func)}.
 */
public abstract class IntList {

    public abstract void clear();
    public abstract int get(int index);
    public abstract void add(int value);
    public abstract void remove(int index);
    public abstract int size();

    public static IntList create() { return factory.run(); }
    public static void init(Func<IntList> factory) { IntList.factory = factory; }
    private static Func<IntList> factory;
}
