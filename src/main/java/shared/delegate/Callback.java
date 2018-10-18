package shared.delegate;

public interface Callback<T> {

    void onComplete(T result);
    void onFailure(Throwable error);

}
