package shared.binding;

/**
 * Some components, such as anchor and button, do not have enough knowledge to perform synchronisation.
 * This interface can be used as a hook that any component can add as property for referencing external sync logic.
 *
 * <code>
 *     public class Spinner extends UI implements HasSync {
 *
 *         SyncDelegate<Integer> syncDelegate;
 *
 *         ... getter and setter of the syncDelegate.
 *
 *         public void sync() {
 *             if (syncDelegate != null) {
 *                 syncDelegate.sync(getSpinnerValue());
 *             }
 *         }
 *     }
 *
 * </code>
 *
 *
 *
 * @param <T>
 */
//@FunctionalInterface can be re-enabled when Android supports Java 8
public interface SyncDelegate<T> {

    void sync(T source);

}
