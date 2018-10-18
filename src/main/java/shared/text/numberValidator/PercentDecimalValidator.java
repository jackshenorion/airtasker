package shared.text.numberValidator;

import util.shared.ObjectUtil;

public class PercentDecimalValidator extends NumberValidator<Double> {

    public static final double DEFAULT_MAX_DOUBLE = 1;
    public static final double DEFAULT_MIN_DOUBLE = 0;

    public PercentDecimalValidator() {
        setMinValue(DEFAULT_MIN_DOUBLE);
        setMaxValue(DEFAULT_MAX_DOUBLE);
    }

    @Override
    public Double getValueInRange(String text) {
        NumberResult result = parse(text);
        if (result.isValid()) {
            return (Double) result.getValue();
        }
        else if (result.getState() == ResultState.IsAboveMaxValue) {
            return getMaxValue();
        }
        else if (result.getState() == ResultState.IsBelowMinValue) {
            return getMinValue();
        }
        return null;
    }

    @Override
    public NumberResult parse(String text) {
        Double v = ObjectUtil.toDouble(text, null);
        // invalid number
        if (v == null) {
            return NumberResult.invalid();
        }
        else if (v < getMinValue()) {
            return NumberResult.exceedMin();
        }
        else if (v > getMaxValue()) {
            return NumberResult.exceedMax();
        }
        return NumberResult.ok(v);
    }
}
