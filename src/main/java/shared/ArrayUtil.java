package shared;

import util.shared.delegate.UnaryAction;

public class ArrayUtil {

	/**
	 * Checks whether the first array ends with the second. Allowing for null.
	 * 
	 * @param array1 -
	 *            the first array to compare.
	 * @param array2 -
	 *            the second array to compare.
	 * @return true if the first array ends with the second, false otherwise.
	 */
	public static boolean endsWith(Object array1[], Object array2[]) {
		
		if (array1 == null || array2 == null)
			return false;
		
		int l = array1.length;
		int r = array2.length;
		if (r > l)
			return false;

        for (int i = 0; i < r; i++)
			if (!ObjectUtil.equals(array1[l - i - 1], array2[r - i - 1]))
				return false;
		return true;
	}
	

	/**
	 * Compares two arrays, and returns true if they have same/idential elements, false otherwise.
	 * <p>
	 * For Example
	 * <PRE>
	 *   Integer[] a = {1,2,3};
	 *   Integer[] b = {2,3,4};
	 *   boolean equals = ArrayUtil.arrayEquals(a, b); // false;
	 *   
	 *   String[] a = {"A", "B"}; 
	 *   String[] b = {"A", "B"}; 
	 *   boolean equals = ArrayUtil.arrayEquals(a, b); // true;
	 * </PRE>
	 * 
	 * @param array1 -
	 *            the first array.
	 * @param array2 -
	 *            the second array.
	 * @returns true if the two arrays have same/equal elements, false otherwise.
	 */
	public static boolean equals(Object[] array1, Object[] array2) {
		
		if (array1 == null)
			return array2 == null;
		
		if (array2 == null)	
			return false;
		
		int leftLength = array1.length;
		int rightLength = array2.length;
		
		if (leftLength != rightLength)
			return false;
		
		for (int i = 0; i < leftLength; i++) {
            if (!ObjectUtil.equals(array1[i], array2[i]))
				return false;
		}
		return true;
		
	}

	/**
	 * A convenient method for iterating through an array and perform action on each of the elements.
	 *
	 * It is mainly for the environments that don't support Java 8 forEach.
     */
	public static <T> void forEach(T[] a, UnaryAction<T> action) {
		if (a == null)
			throw new NullPointerException();

		for (int i = 0; i < a.length; i++) {
			action.run(a[i]);
		}
	}

	/**
	 * Searches for the first occurence of the second argument in the given
	 * array.
	 * 
	 * @param a -
	 *            the object array to be searched from, must not be null.
	 * @param element -
	 *            the element to be searched for, may be null.
	 * @return the index of the element, or returns -1 if the element is not
	 *         found in the array.
	 */
	public static int indexOf(Object[] a, Object element) {
	    if (a == null)
	    	throw new NullPointerException();
	    
	    for (int i = 0; i < a.length; i++) {
            if (ObjectUtil.equals(element, a[i]))
				return i;
		}
	    return -1;
	}

	/**
	 * Searches for the first occurence of a string in a string array,
	 * irrespective of case differences. Returns -1 if the string is not found.
	 * 
	 * @param a -
	 *            the string array to search from.
	 * @param string -
	 *            the string to search for.
	 * @return the index of the element, or returns -1 if the string is not
	 *         found in the array.
	 */
	public static int indexOfIgnoreCase(String[] a, String string) {
		return indexOfIgnoreCase(a, string, 0);
	}

	/**
	 * Searches for the first occurence of the specified element in a string
	 * array, starts from the specified index, irrespective of case differences. 
	 * 
	 * @param a -
	 *            the string array to be searched from.
	 * @param string -
	 *            the string to be searched for.
	 * @param from -
	 *            the index of start searching
	 * @return the index of the element, or returns -1 if the string is not
	 *         found in the array.
	 */
	public static int indexOfIgnoreCase(String[] a, String string, int from) {
		if (a == null)
			throw new NullPointerException();
		if (from < 0)
			throw new IllegalArgumentException("from is negative.");
	
	    for (int i = from; i < a.length; i++) {
			if (Strings.equalsIgnoreCase(string, a[i]))
				return i;
		}
		return -1;
	}


	/**
	 * Build a string by concatnating all elements in a string array with
	 * specified character as glue. The runtime type of the returned array is
	 * that of the specified array. Null value is ignored.
	 * 
	 * <p>
	 * For example:
	 * 
	 * <pre>
	 * <li>
	 * String[] a = { &quot;A&quot;, &quot;B&quot;, &quot;C&quot; };
	 * System.out.println(join('|', a)); // A|B|C
	 * </li>
	 * <li>
	 * String[] a = { &quot;A&quot;, null, &quot;C&quot; };
	 * System.out.println(join('|', a)); // A|C
	 * </li>
	 * <li>
	 * String[] a = {};
	 * System.out.println(join('|', a)); // (empty string)
	 * </li>
	 * </pre>
	 * 
	 * @param glue -
	 *            the character to join the array.
	 * @param a -
	 *            the array to be joined, must not be null.
	 * @return joined array.
	 */
	public static String join(char glue, String[] a) {
		return ArrayUtil.join(Character.toString(glue), a);
	}

	/**
	 * Build a string by concatnating all elements in a string array with
	 * specified string as glue. The runtime type of the returned array is that
	 * of the specified array. Null value is ignored.
	 * 
	 * <p>
	 * For example:
	 * </p>
	 * 
	 * <pre>
	 * <li>
	 * String[] a = { &quot;A&quot;, &quot;B&quot;, &quot;C&quot; };
	 * System.out.println(join(&quot;|&quot;, a)); // A|B|C
	 * </li>
	 * <li>
	 * String[] a = { &quot;A&quot;, null, &quot;C&quot; };
	 * System.out.println(join(&quot;|&quot;, a)); // A|C
	 * </li>
	 * <li>
	 * String[] a = {};
	 * System.out.println(join(&quot;|&quot;, a)); // (empty string)
	 * </li>
	 * <li>
	 * String[] a = { &quot;A&quot;, null, &quot;C&quot; };
	 * System.out.println(join(null, a)); // AC
	 * </li>
	 * </pre>
	 * 
	 * @param glue -
	 *            the String to join the array, may be null.
	 * @param a -
	 *            the array to be joined, must not be null.
	 * @return joined array.
	 */
	public static String join(String glue, String[] a) {
		if (a == null)
			throw new NullPointerException();
	
		int len = a.length;
		if (len == 0) {
			return "";
		}
		StringBuilder buf = new StringBuilder(100);
		buf.append(Strings.toNotNull(a[0]));
		for (int i = 1; i < len; ++i) {
			if (a[i] == null)
				continue;
			if (glue != null)
				buf.append(glue);
			buf.append(Strings.toNotNull(a[i]));
		}
		return buf.toString();
	}
	
	public static String join(String glue, String[] a, boolean ignoreNullOrEmpty) {
		if (a == null)
			throw new NullPointerException();
	
		int len = a.length;
		if (len == 0) {
			return "";
		}
		StringBuilder buf = new StringBuilder(100);
		buf.append(Strings.toNotNull(a[0]));
		for (int i = 1; i < len; ++i) {
			if (Strings.isNullOrEmpty(a[i]) && ignoreNullOrEmpty) {
                continue;
            }			
			if (glue != null && buf.length() > 0)
				buf.append(glue);
			buf.append(Strings.toNotNull(a[i]));
		}
		return buf.toString();
	}

	/**
	 * Trims all not-null element of the given string array. Null element is
	 * ignored.
	 * 
	 * @param array -
	 *            the source array, must not be null.
	 * @return the result array.
	 */
	public static String[] trimAll(String[] array) {
		Precondition.checkNotNull(array);
		for (int i = 0; i < array.length; i++) {
			array[i] = Strings.trim(array[i]);
		}
		return array;
	}

	public static Short[] wrap(short[] s) {
		Short[] a = new Short[s.length];
		for (int i = 0; i < s.length; i++) {
			a[i] = s[i];
		}
		return a;
	}

	public static Long[] wrap(long[] l) {
		Long[] a = new Long[l.length];
		for (int i = 0; i < l.length; i++) {
			a[i] = l[i];
		}
		return a;
	}

	public static Integer[] wrap(int[] is) {
		Integer[] a = new Integer[is.length];
		for (int i = 0; i < is.length; i++) {
			a[i] = is[i];
		}
		return a;
	}

	public static Float[] wrap(float[] f) {
		Float[] a = new Float[f.length];
		for (int i = 0; i < f.length; i++) {
			a[i] = f[i];
		}
		return a;
	}

	public static Double[] wrap(double[] d) {
		Double[] a = new Double[d.length];
		for (int i = 0; i < d.length; i++) {
			a[i] = d[i];
		}
		return a;
	}

	public static Character[] wrap(char[] c) {
		Character[] a = new Character[c.length];
		for (int i = 0; i < c.length; i++) {
			a[i] = c[i];
		}
		return a;
	}

	public static Byte[] wrap(byte[] b) {
		Byte[] a = new Byte[b.length];
		for (int i = 0; i < b.length; i++) {
			a[i] = b[i];
		}
		return a;
	}

	public static Boolean[] wrap(boolean[] b) {
		Boolean[] a = new Boolean[b.length];
		for (int i = 0; i < b.length; i++) {
			a[i] = b[i];
		}
		return a;
	}

	public static int[] unwrap(Integer[] intArray) {
		int[] data = new int[intArray.length];
		for (int i = 0; i < data.length; i++) {
			data[i] = intArray[i];
		}
		return data;
	}

}
