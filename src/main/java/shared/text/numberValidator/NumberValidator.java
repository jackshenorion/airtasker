package shared.text.numberValidator;

public abstract class NumberValidator<T extends Number> {

    private T maxValue;
    private T minValue;

    public T getMinValue() {
        return minValue;
    }

    public NumberValidator<T> setMinValue(T minValue) {
        this.minValue = minValue;
        return this;
    }

    public T getMaxValue() {
        return maxValue;
    }

    public NumberValidator<T> setMaxValue(T maxValue) {
        this.maxValue = maxValue;
        return this;
    }

    // get valid number within bounds
    public abstract T getValueInRange(String text);

    // get result object with detailed information
    public abstract NumberResult parse(String text);
}
