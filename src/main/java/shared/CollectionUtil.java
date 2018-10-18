package shared;

import util.shared.delegate.EqualityComparator;
import util.shared.delegate.UnaryFunc;
import util.shared.format.ValueFormatter;

import java.util.*;

/**
 * Utility method for collection.
 * 
 * @author
 * @version 1.0
 */
public class CollectionUtil {

	public static <T> List<T> clone(List<T> list, UnaryFunc<T, T> cloneFunc) {
		ArrayList<T> clone = new ArrayList<>();
		for (T elem : list) {
			if (elem != null) {
				clone.add(cloneFunc.run(elem));
			}
		}
		return clone;
	}

	/**
	 * Compares two lists. Allowing for null - two null lists are considered to
	 * be equal.
     *
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	public static int compares(List<? extends Comparable<?>> list1, List<? extends Comparable<?>> list2) {
		if (list1 == null && list2 == null)
			return 0;
		if (list1 == null)
			return -1;
		if (list2 == null)
			return 1;
		int l = list1.size();
		int r = list2.size();
		if (l != r)
			return l - r;
		for (int i = 0; i < l; i++) {
			int compareTo = ObjectUtil.compares(list1.get(i), list2.get(i));
			if (compareTo != 0)
				return compareTo;
		}
		return 0;
	}

    /**
     * Return true if the given collection contains any of the specified items, false otherwise.
     */
    public static <T> boolean containsAny(Collection<T> collection, T ... items) {
        return containsAny(collection, Arrays.asList(items));
    }

	public static <T> boolean containsAny(Collection<T> collection, Collection<T> items) {
		for (T item : items) {
			if (collection.contains(item)) {
				return true;
			}
		}
		return false;
	}

	public static <T> boolean equals(List<T> list1, List<T> list2, EqualityComparator<T> comparator) {
		if (list1 == null && list2 == null)
			return true;
		if (list1 == null || list2 == null)
			return false;
		int l = list1.size();
		int r = list2.size();
		if (l != r)
			return false;
		for (int i = 0; i < l; i++) {
			if (!ObjectUtil.equals(list1.get(i), list2.get(i), comparator)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Search for item using custom comparator.
     */
	public static <T> int indexOf(List<T> list, T item, EqualityComparator<T> comparator) {
		for (int i = 0, total = list.size(); i < total; i++) {
			if (comparator.equals(list.get(i), item)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Returns true if the given Collection is null or empty, false otherwise.
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return (collection == null || collection.isEmpty());
	}

    /**
     * Returns true if the given map is null or empty, false otherwise.
     */
	public static boolean isEmpty(Map<?, ?> map) {
		return (map == null || map.isEmpty());
	}
	
	/**
	 * Build a string by concatenating all elements in a collection with
	 * specified delimiter.
	 */
	public static String join(String delimiter, Collection<String> c, boolean ignoreEmpty) {
        return join(delimiter, c, item -> item, ignoreEmpty);
	}

    /**
     * Build a string by concatenating all elements in a collection with
     * specified delimiter.
     */
    public static <T> String join(String delimiter, Collection<T> c, ValueFormatter<T> formatter, boolean ignoreEmpty) {
        if (c == null)
            throw new NullPointerException();

        if (c.size() == 0) {
            return "";
        }

        Iterator<T> iter = c.iterator();
		
		StringBuilder buff = new StringBuilder(100);
		buff.append(Strings.toNotNull(formatter.format(iter.next()))); // call Util.getString() to prevent null.
		
		while(iter.hasNext()) {
			String next = Strings.toNotNull(formatter.format(iter.next()));
			if (ignoreEmpty && next.length() == 0) {
				continue;
			}
			if (delimiter != null && buff.length() > 0) {
				buff.append(delimiter);
			}
			buff.append(next);
		}
		
		return buff.toString();
    }

	/**
	 * A convenient way to create an ArrayList with null elements ignored.
	 */
	@SafeVarargs
	public static <T> List<T> list(T... elements) {
		ArrayList<T> list = new ArrayList<>();
		for (T elem : elements) {
			if (elem != null) {
				list.add(elem);
			}
		}
		return list;
	}

	/**
	 * A convenient way to create an Set with null elements ignored.
	 */
	@SafeVarargs
	public static <T> Set<T> set(T... elements) {
		HashSet<T> set = new HashSet<>();
		for (T elem : elements) {
			if (elem != null) {
				set.add(elem);
			}
		}
		return set;
	}

    /**
     * A convenient way to create an ArrayList with nullable item.
     */
    @SafeVarargs
    public static <T> List<T> nullableList(T... elements) {
        ArrayList<T> list = new ArrayList<>();
		Collections.addAll(list, elements);
        return list;
    }

	/**
	 * Merge two lists and return a new list.
	 */
	public static <T> List<T> merge(List<T> list1, List<T> list2) {
		List<T> set = new ArrayList<>(list1);
		set.addAll(list2);
		return new ArrayList<>(set);
	}

    /**
     * Merge two lists and return a new list with unique items.
     */
    public static <T> List<T> mergeUnique(List<T> list1, List<T> list2) {
        LinkedHashSet<T> set = new LinkedHashSet<>(list1);
        set.addAll(list2);
        return new ArrayList<>(set);
    }

	public static int[] unwrap(List<Integer> intList) {
		int[] data = new int[intList.size()];
		for (int i = 0; i < data.length; i++) {
			data[i] = intList.get(i);
		}
		return data;
	}

}
