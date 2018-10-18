package shared.delegate;

/**
 * A predicate with one parameter.
 *
 */
//@JsFunction
@FunctionalInterface
public interface Predicate<P> {

    boolean run(P p);

}