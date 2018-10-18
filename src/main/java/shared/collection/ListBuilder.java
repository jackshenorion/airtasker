package shared.collection;

import util.shared.delegate.Func;

import java.util.ArrayList;
import java.util.List;

/**
 * ListBuilder is a utility class help building a list in fluent style with null item ignored. It has zero overhead on performance.
 *
 * E.g.
 *    List<Item> = new ListBuild<>()
 *      .append(singleItem)
 *      .append(itemArray)
 *      .append(itemList)
 *      .toList();
 *
 */
public final class ListBuilder<T> {

    private List<T> list;

    public ListBuilder() {
        list = new ArrayList<>();
    }

    /**
     * Append an item to the list. Null item is ignored.
     */
    public ListBuilder<T> append(T item) {
        if (item != null) {
            list.add(item);
        }
        return this;
    }

    /**
     * Append an item to the list. Null item is ignored.
     */
    public ListBuilder<T> append(Func<T> item) {
        append(item.run());
        return this;
    }

    /**
     * Append an array of items to the list. Null item is ignored.
     */
    public ListBuilder<T> append(T... items) {
        for (T item : items) {
            append(item);
        }
        return this;
    }

    /**
     * Append a list of items to the list. Null item is ignored.
     */
    public ListBuilder<T> append(List<T> items) {
        for (T item : items) {
            append(item);
        }
        return this;
    }

    /**
     * Build a list.
     */
    public List<T> toList() {
        return list;
    }
}
