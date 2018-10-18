package shared.delegate;

import java.util.logging.Logger;

public final class Default {

    public static final Action ACTION = () -> {};
    public static final UnaryAction UNARY_ACTION = p -> {};
    public static final BiAction BI_ACTION = (p1, p2) -> {};

    public static final Predicate TRUE = param -> true;
    public static final Predicate FALSE = param -> false;

    public static final Func<Boolean> TRUE_FUNC = () -> true;
    public static final Func<Boolean> FALSE_FUNC = () -> false;

    public static <T> Callback<T> EmptyCallback() {
        return new Callback<T>() {
            @Override
            public void onComplete(T result) {}

            @Override
            public void onFailure(Throwable error) {
                Logger.getGlobal().severe(error.getMessage());
            }
        };
    }


}
