package shared.text.numberValidator;

public final class NumberResult<T extends Number> {

    private ResultState state;
    private T value;

    public ResultState getState() {
        return state;
    }

    public static NumberResult ok(Number i) {
        return new NumberResult(ResultState.IsValid, i);
    }

    public static NumberResult invalid() {
        return new NumberResult(ResultState.IsNotNumber, null);
    }

    public static NumberResult exceedMax() {
        NumberResult result = new NumberResult(ResultState.IsAboveMaxValue, null);
        result.state = ResultState.IsAboveMaxValue;
        return result;
    }

    public static NumberResult exceedMin() {
        NumberResult result = new NumberResult(ResultState.IsBelowMinValue, null);
        result.state = ResultState.IsBelowMinValue;
        return result;
    }

    public boolean isValid() {
        return state == ResultState.IsValid;
    }

    public T getValue() {
        return value;
    }

    public NumberResult setValue(T value) {
        this.value = value;
        return this;
    }

    protected NumberResult(ResultState state, T value) {
        this.state = state;
        this.value = value;
    }

}