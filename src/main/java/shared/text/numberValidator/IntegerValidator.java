package shared.text.numberValidator;

import util.shared.ObjectUtil;

public class IntegerValidator extends NumberValidator<Integer> {

    public static final int DEFAULT_MAX_INT = 100_000_000;
    public static final int DEFAULT_MIN_INT = -100_000_000;

    public static NumberValidator<Integer> NON_NEGATIVE_NUMBER = new IntegerValidator().setMinValue(0).setMaxValue(DEFAULT_MAX_INT);

    public IntegerValidator() {
        setMinValue(DEFAULT_MIN_INT);
        setMaxValue(DEFAULT_MAX_INT);
    }

    @Override
    public Integer getValueInRange(String text) {
        NumberResult result = parse(text);
        if (result.isValid()) {
            return (Integer) result.getValue();
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
        // user should be allowed to type in comma but will be replaced with proper formatting
        text = text.replaceAll("[,$]", "");
        Long v = ObjectUtil.toLong(text, null);
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
        return NumberResult.ok(ObjectUtil.toInt(text, null));
    }
}
