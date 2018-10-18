package shared.diff;


public interface DiffComparator<T> {

    void compare(T oldNode, T newNode, DiffBuilder<T> diff);

}
