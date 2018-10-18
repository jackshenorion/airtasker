package shared.collection.impl;

import util.shared.collection.IntList;
import util.shared.delegate.Func;

import java.util.ArrayList;

/**
 * This is the default implementation of {@link IntList} based on {@link ArrayList}
 *
 * You should not use this class directly.
 */
public final class DefaultIntList extends IntList {

    private ArrayList<Integer> list = new ArrayList<>();

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public int get(int index) {
        return list.get(index);
    }

    @Override
    public void add(int value) {
        list.add(value);
    }

    @Override
    public void remove(int index) {
        list.remove(index);
    }

    @Override
    public int size() {
        return list.size();
    }

    public static Func<IntList> Factory = DefaultIntList::new;
}
