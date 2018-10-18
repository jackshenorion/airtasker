package shared.collection.impl;

import util.shared.collection.IntSet;
import util.shared.delegate.Func;

import java.util.HashSet;
import java.util.Set;

/**
 * This is the default implementation of {@link IntSet} based on {@link HashSet}
 *
 * You should not use this class directly.
 */
public final class DefaultIntSet extends IntSet {

    private HashSet<Integer> set = new HashSet<>();

    @Override
    public void add(int value) {
        set.add(value);
    }

    @Override
    public void clear() {
        set.clear();
    }

    @Override
    public boolean contains(int value) {
        return set.contains(value);
    }

    @Override
    public boolean remove(int index) {
        return set.remove(index);
    }

    @Override
    public int size() {
        return set.size();
    }

    @Override
    public int[] toArray() {
        return toInts(set);
    }

    public static Func<IntSet> Factory = DefaultIntSet::new;


    static int[] toInts(Set<Integer> set) {
        int[] intKeys = new int[set.size()];
        int i = 0;
        for (Integer value : set) {
            intKeys[i] = value;
            i++;
        }
        return intKeys;
    }
}
