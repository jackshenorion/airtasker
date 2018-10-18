package shared;

import java.util.Collection;
import java.util.Map;

public final class Precondition {

    public static final String FAILED_MESSAGE = "Precondition check failed";

    private Precondition() {
	}

    public static void checkTrue(boolean expression) {
        if (!expression) fail(FAILED_MESSAGE);
    }

	public static void checkTrue(boolean expression, String message) {
		if (!expression) fail(message);
	}

    public static void checkFalse(boolean expression) {
        if (expression) fail(FAILED_MESSAGE);
    }

	public static void checkFalse(boolean expression, String message) {
        if (expression) fail(message);
	}

    public static void checkEquals(Object expected, Object actual) {
        checkEquals(expected, actual, null);
    }

	public static void checkEquals(Object expected, Object actual, String message) {
		if (expected == null && actual == null)
			return;
		if (expected != null && expected.equals(actual))
			return;
		if (message == null) {
			fail("Expected: " + expected + ", actual value is: " + actual);
		}
		else {
			fail(message);
		}
	}

	public static void checkNotNull(Object object) {
		if (object == null) fail(FAILED_MESSAGE);
	}

	public static void checkNotNull(Object object, String message) {
        if (object == null) fail(message);
	}

	public static void checkNull(Object object) {
        if (object != null) fail(FAILED_MESSAGE);
	}

	public static void checkNull(Object object, String message) {
        if (object != null) fail(message);
	}

    public static void checkSame(Object expected, Object actual) {
        if(expected != actual) fail(FAILED_MESSAGE);
    }

	public static void checkSame(Object expected, Object actual, String message) {
        if(expected != actual) fail(message);
	}

	public static void checkNotEmpty(String s) {
        checkNotEmpty(s, FAILED_MESSAGE);
	}

	public static void checkNotEmpty(String s, String message) {
        if (s == null || s.length() == 0) fail(message);
	}

    public static void checkEmpty(String s) {
        checkEmpty(s, FAILED_MESSAGE);
    }

    public static void checkEmpty(String s, String message) {
        if (s != null && s.length() > 0) fail(message);
    }

    public static void checkNotSame(Object expected, Object actual) {
        checkNotSame(expected, actual, "");
    }

	public static void checkNotSame(Object expected, Object actual, String message) {
		if (expected == actual) fail(message);
	}

    public static void checkNotEmpty(Object[] array) {
        checkNotEmpty(array, FAILED_MESSAGE);
    }

	public static void checkNotEmpty(Object[] array, String message) {
		if (array == null || array.length == 0) {
			fail(message);
		}
	}

    public static void checkNotEmpty(Collection<?> collection) {
        checkNotEmpty(collection, FAILED_MESSAGE);
    }

	public static void checkNotEmpty(Collection<?> collection, String message) {
		if (CollectionUtil.isEmpty(collection)) {
			fail(message);
		}
	}

    public static void checkNotEmpty(Map<?, ?> map) {
        checkNotEmpty(map, FAILED_MESSAGE);
    }

	public static void checkNotEmpty(Map<?, ?> map, String message) {
		if (CollectionUtil.isEmpty(map)) {
			fail(message);
		}
	}

    public static void checkPositiveNumber(int num) {
        checkPositiveNumber(num, FAILED_MESSAGE);
    }

    public static void checkPositiveNumber(int num, String msg) {
        if (num < 0) {
            fail(msg);
        }
    }
	private static void fail(String message) {
		throw new IllegalArgumentException(message);
	}

}