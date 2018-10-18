package shared.http;

public final class HttpFactory {

    public static HttpGet createGet() {
        return impl.createGet();
    }

    public static HttpPost createPost() {
        return impl.createPost() ;
    }

    public static void init(Impl httpFactoryImpl) {
        HttpFactory.impl = httpFactoryImpl;
    }

    private static Impl impl = null;

    public interface Impl {

        HttpGet createGet();
        HttpPost createPost();

    }

}
