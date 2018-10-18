package shared.linq;

import util.shared.ObjectUtil;
import util.shared.delegate.*;

import java.util.*;

/**
 * Linq is a utility to extract and process data with query expression which are akin to
 * SQL statements.
 *
 * E.g. calculate the average salary of senior employees who have worked over 5 years.
 *
 *   List<Employee> employees = ...;
 *   double averageSalary = Linq.from(employees)
 *                          .where(employee -> employee.getYearsOfService() > 5)
 *                          .select(employee -> employee.getSalary())
 *                          .average();
 *
 * @param <T>
 */
public class Linq<T> {

    /**
     * Create a linq instance for the given array. Operation within linq doesn't affect the original array.
     */
	public static <T> Linq<T> from(T... dataSource) {
		List<T> copy = new ArrayList<>();
		Collections.addAll(copy, dataSource);
		return wrap(copy);
	}

    /**
     * Create a linq instance for the given data source. Operation within linq doesn't affect the original data source.
     */
	public static <T> Linq<T> from(Iterable<T> dataSource) {
        List<T> copy = new ArrayList<>();
        for (T item : dataSource) {
            copy.add(item);
        }
        return wrap(copy);
    }

    /**
     * Create a linq instance wrapping the given list. This method works best if
     *
     *  - only immutating methods (where, select, etc) will be called so the given list will not changed, or
     *  - the given list is fine to be modified by mutating methods like sort
     */
    public static <T> Linq<T> wrap(List<T> list) {
        return new Linq<>(list);
    }

    private List<T> source;

    private Linq(List<T> source) {
        this.source = source;
    }

    public boolean all(Predicate<T> predicate) {
        for (T item : source) {
			if (!predicate.run(item)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean any() {
        return source.size() > 0;
	}
	
	public boolean any(Predicate<T> predicate) {
        for (T item : source) {
			if (predicate.run(item)) {
				return true;
			}
		}
		return false;
	}
	
	public double average() {
		if (source.isEmpty()) {
			return 0d;
		}
		
		double total = 0;
		for (T n : source) {
			total += ((Number) n).doubleValue();
		}
		return total / source.size();
	}
	
	public double average(UnaryFunc<T, Number> selector) {
		double total = 0;
		int count = 0;
        for (T t : source) {
            total += selector.run(t).doubleValue();
            count++;
		}
		return total / count;
	}
	
	public Linq<T> concat(Collection<T> target) {
        source.addAll(target);
		return this;
	}
	
	public boolean contains(T value) {
		return source.contains(value);
	}
	
	public boolean contains(T value, EqualityComparator<T> comparator) {
        for (T item : source) {
			if (comparator.equals(item, value)) {
				return true;
			}
		}
		return false;
	}
	
	public int count() {
		return source.size();
	}
	
	public int count(Predicate<T> predicate) {
        int count = 0;
        for (T item : source) {
            if (predicate.run(item)) {
                count++;
            }
        }
		return count;
	}
	
	public Linq<T> distinct() {
        source = new ArrayList<>(new HashSet(source));
        return this;
	}

	public Linq<T> distinct(UnaryFunc<T, Comparable<?>> keySelector) {
		Map<Comparable<?>, T> map = new HashMap<>();
		for (T item : source) {
			map.put(keySelector.run(item), item);
		}
		source = new ArrayList<>(map.values());
		return this;
	}

    public Linq<T> forEach(UnaryAction<T> action) {
        for (T item : source) {
            action.run(item);
        }
        return this;
    }

	public Linq<T> forEach(IntBiAction<T> action) {
		for (int i = 0; i < source.size(); i++) {
			action.run(i, source.get(i));
		}
		return this;
	}

	public T elementAt(int index) {
        if (index < 0 || index >= source.size()) {
            return null;
        }
        return source.get(index);
	}
	
	public Linq<T> empty() {
        source.clear();
		return this;
	}
	
	public Linq<T> except(Collection<T> target) {
        LinkedHashSet<T> set = new LinkedHashSet<>(source);
		set.removeAll(target);
        source = new ArrayList<>(set);
		return this;
	}
	
	public T first() {
		return source.isEmpty() ? null : source.get(0);
	}

	public T firstOrElse(T returnIfNull) {
    	T first = first();
		return first == null ? returnIfNull : first;
	}
	
	public T first(Predicate<T> predicate) {
        for (T item : source) {
            if (predicate.run(item)) {
                return item;
            }
        }
		return null;
	}

	public int indexOf(T value, EqualityComparator<T> comparator) {
		int index = 0;
		for (T item : source) {
			if (comparator.equals(item, value)) {
				return index;
			}
			index += 1;
		}
		return -1;
	}

	public Linq<T> intersect(Collection<T> second) {
        LinkedHashSet<T> set = new LinkedHashSet<>(source);
		set.retainAll(second);
        source = new ArrayList<>(set);
		return this;
	}

	public T last() {
        int size = source.size();
        return size == 0 ? null : source.get(size - 1);
	}
	
	public T last(Predicate<T> predicate) {
        for (int i = source.size() - 1; i > -1; i--) {
            T item = source.get(i);
            if (predicate.run(item)) {
                return item;
            }
        }
        return null;
	}

	public T max() {
        Iterator<T> iter = source.iterator();
		if (!iter.hasNext()) {
			return null;
		}
		
		T max = iter.next();
		while (iter.hasNext()) {
			T item = iter.next();
            if (ObjectUtil.compares((Comparable) item, (Comparable) max) > 0) {
                max = item;
            }
		}
		return max;
	}

	public T max(UnaryFunc<T, Comparable> selector) {
		Iterator<T> iter = source.iterator();
		if (!iter.hasNext()) {
			return null;
		}

		T maxItem = iter.next();
		Comparable<?> maxValue = selector.run(maxItem);
		while (iter.hasNext()) {
			T nextItem = iter.next();
			Comparable<?> nextValue = selector.run(nextItem);

			if (ObjectUtil.compares(nextValue, maxValue) > 0) {
				maxItem = nextItem;
				maxValue = nextValue;
			}
		}

		return maxItem;
	}

	public int maxInt(int returnIfNull) {
		Number max = ((Number) max());
    	return max != null ? max.intValue() : returnIfNull;
	}

	public double maxDouble(double returnIfNull) {
		Number max = ((Number) max());
		return max != null ? max.doubleValue() : returnIfNull;
	}

	public T min() {
        Iterator<T> iter = source.iterator();
		if (!iter.hasNext()) {
			return null;
		}
		
		T min = iter.next();
		while (iter.hasNext()) {
			T item = iter.next();
            if (ObjectUtil.compares((Comparable) item, (Comparable) min) < 0) {
				min = item;
			}
		}
		return min;
	}

	public T min(UnaryFunc<T, Comparable> selector) {
		Iterator<T> iter = source.iterator();
		if (!iter.hasNext()) {
			return null;
		}

		T minItem = iter.next();
		Comparable<?> minValue = selector.run(minItem);
		while (iter.hasNext()) {
			T nextItem = iter.next();
			Comparable<?> nextValue = selector.run(nextItem);

			if (ObjectUtil.compares(nextValue, minValue) < 0) {
				minItem = nextItem;
				minValue = nextValue;
			}
		}

		return minItem;
	}

	public <K extends Comparable<K>> Linq<T> orderBy(UnaryFunc<T, K> keySelector) {
        return orderBy(keySelector, ObjectUtil::compares);
	}
	
	public <K> Linq<T> orderBy(UnaryFunc<T, K> keySelector, Comparator<K> comparator) {
		Collections.sort(source, new Comparator<T>() {
			public int compare(T o1, T o2) {
				K key1 = keySelector.run(o1);
				K key2 = keySelector.run(o2);
				return comparator.compare(key1, key2);
			}
		});
		return this;
	}

	public Linq<T> orderBy(Comparator<T> comparator) {
		Collections.sort(source, comparator);
		return this;
	}

	public Linq<T> orderByDescending(Comparator<T> comparator) {
		Collections.sort(source, (o1, o2) -> -comparator.compare(o1, o2));
		return this;
	}

	public <K> Linq<T> orderByDescending(UnaryFunc<T, K> keySelector, Comparator<K> comparator) {
		return orderBy(keySelector, (k1, k2) -> -comparator.compare(k1, k2));
	}

	public <K extends Comparable<K>> Linq<T> orderByDescending(UnaryFunc<T, K> keySelector) {
        return orderBy(keySelector, (k1, k2) -> -ObjectUtil.compares(k1, k2));
	}

	public Linq<T> range(int start, int count) {
        List<T> result = new ArrayList<>(count);
		for (int i = start, end = start + count, total = source.size(); i < end && i < total; i++) {
			result.add(source.get(i));
		}
        source = result;
		return this;
	}
	
	public Linq<T> repeat(T item, int count) {
        source.clear();
		for (int i = 0; i < count; i++) {
			source.add(item);
		}
		return this;
	}
	
	public Linq<T> reverse() {
		Collections.reverse(source);
		return this;
	}

	public <K> Linq<K> select(UnaryFunc<T, K> selector) {
        List<K> result = new ArrayList<>(source.size());
		for (T item : source) {
			K converted = selector.run(item);
			if (converted != null) {
				result.add(converted);
			}
		}
		return new Linq<>(result);
	}

	public <K> Linq<K> selectMany(UnaryFunc<T, Collection<K>> selector) {
        List<K> out = new ArrayList<>(source.size());
		for (T item : source) {
			out.addAll(selector.run(item));
		}
		return new Linq<>(out);
	}

	public boolean sequenceEqual(Collection<T> target) {
        if (target instanceof List){
            return source.equals((List) target);
        }
        else {
            return source.equals(new ArrayList<>(target));
        }
	}

	public T single() {
        return first();
	}
	
	public T single(Predicate<T> predicate) {
        return first(predicate);
	}

	public Linq<T> skip(int count) {
        List<T> result = new ArrayList<>();
		for (int i = count, k = source.size(); i < k; i++) {
			result.add(source.get(i));
		}
        source = result;
		return this;
	}
	
	public Linq<T> skip(Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
		
		for (T item : source) {
            if (!predicate.run(item)) {
                result.add(item);
            }
        }
        source = result;
        return this;
	}
	
	public Linq<T> skip(BiPredicate<T, Integer> predicate) {
        List<T> result = new ArrayList<>();
		
		int index = 0;
		for (T item : result) {
			if (!predicate.test(item, index)) {
				result.add(item);
			}
			index++;
		}
        source = result;
        return this;
	}
	
	public int sumInt() {
        int sum = 0;
		for (T n : source) {
			sum += ((Number)n).intValue();
		}
		return sum;
	}
	
	public int sumInt(UnaryFunc<T, Integer> function) {
        int sum = 0;
		for (T item : source) {
            sum += function.run(item);
		}
		return sum;
	}

	public int sumLong(UnaryFunc<T, Long> function) {
		int sum = 0;
		for (T item : source) {
			sum += function.run(item);
		}
		return sum;
	}
	
	public double sumDouble() {
        double sum = 0;
		for (T n : source) {
			sum += ((Number) n).doubleValue();
		}
		return sum;
	}
	
	public double sumDouble(UnaryFunc<T, Double> function) {
        double sum = 0;
		for (T item : source) {
			sum += function.run(item);
		}
		return sum;
	}

	public Linq<T> take(int count) {
        List<T> result = new ArrayList<>();
        for (int i = 0, total = source.size(); i < count && i < total; i++) {
			result.add(source.get(i));
		}
        source = result;
        return this;
	}

	public Linq<T> where(Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T item : source) {
            if (predicate.run(item)) {
                result.add(item);
            }
        }
        source = result;
        return this;
	}

    public Linq<T> where(BiPredicate<T, Integer> predicate) {
        List<T> result = new ArrayList<>();
        int index = 0;
        for (T item : source) {
            if (predicate.test(item, index)) {
                result.add(item);
                index++;
            }
        }
        source = result;
        return this;
    }

    public Linq<T> union(Collection<T> target) {
        assert target != null;

        LinkedHashSet<T> result = new LinkedHashSet<>(source);
		result.addAll(target);

        source = new ArrayList<>(result);
		return this;
	}
	
	public <K> Map<K, T> toMap(UnaryFunc<T, K> keySelector) {
        HashMap<K, T> out = new HashMap<>();
		for (T item : source) {
            K key = keySelector.run(item);
            if (key != null) {
                out.put(key, item);
            }
		}
		return out;
	}

	public <K, V> Map<K, V> toMap(UnaryFunc<T, K> keySelector, UnaryFunc<T, V> valueSelector) {
		HashMap<K, V> out = new HashMap<>();
		for (T item : source) {
			K key = keySelector.run(item);
			V val = valueSelector.run(item);
			if (key != null && val != null) {
				out.put(key, val);
			}
		}
		return out;
	}

	public List<T> toList() {
		return source;
	}

	public Set<T> toSet() {
		return new HashSet<>(source);
	}


}
