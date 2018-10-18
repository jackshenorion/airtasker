package shared.json;

public final class JsonNumber extends JsonValue {

	private double value;

	public JsonNumber(double value) {
		this.value = value;
	}

	public JsonNumber(int value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object other) {
		if (other.getClass() != JsonNumber.class) {
			return false;
		}
		return Math.abs(value - ((JsonNumber) other).value) < 0.001;
	}

	@Override
	public int hashCode() {
		return (int) value;
	}

	@Override
	public JsonNumber asNumber() { return this; }

	@Override
	public boolean isNumber() { return true; }

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public String toJsonString() {
		if (value == Math.rint(value)) {
			return Integer.toString((int) value);
		}
		else {
			return Double.toString(value);
		}
	}
}