package shared.http;

import com.google.gwt.core.shared.GwtIncompatible;

public interface HttpMethod<T extends HttpMethod> {

    HttpUrl getUrl();

    /**
     * A template can contain path params. Below are examples:
     *
     *  - domain.com
     *  - domain.com/{id}
     *  - domain.com/{id1}/{id2}
     */
    T setUrl(String urlTemplate);

    T setPathParam(String key, int value);

    T setPathParam(String key, long value);

    T setPathParam(String key, String value);

    T setHeaderParam(String key, long value);

    T setHeaderParam(String key, int value);

    T setHeaderParam(String key, Integer value);

    T setHeaderParam(String key, String value);

    T setContentType(String contentType);

    T setBody(String body);

    @GwtIncompatible
    T setBody(byte[] body);

    T setConnectTimeout(int ms);

	T setReadTimeout(int ms);

}