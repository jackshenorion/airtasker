package shared.delegate;

/**
 * A predicate with two parameters.
 *
 * @param <P1>
 * @param <P2>
 */
//@JsFunction
@FunctionalInterface
public interface BiPredicate<P1, P2> {

    boolean test(P1 p1, P2 p2);

}
