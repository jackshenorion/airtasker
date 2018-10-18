package shared.http;

import com.google.common.annotations.GwtCompatible;
import util.shared.CollectionUtil;
import util.shared.Strings;
import util.shared.json.JsonArray;
import util.shared.json.JsonObject;

import java.util.List;
import java.util.Map;

public class HttpResponse {

    private String url;
    private int statusCode;
    private Map<String, List<String>> headers;
    private HttpContent content;
	
	public HttpResponse() {
	}

    public String getUrl() {
        return url;
    }

    public HttpResponse setUrl(String url) {
        this.url = url;
        return this;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public HttpResponse setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    /**
     * Get HTTP headers. Note: the headers returned may contain null key which maps to status line.
     *
     * @see "HttpURLConnection.getHeaderFields" for more detail
     */
    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public HttpResponse setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
        return this;
    }

	public String getContentType() {
		List<String> types = headers.get("Content-Type");
		return CollectionUtil.isEmpty(types) ? null : types.get(0);
	}

	/**{
	 * Returns a not-null byte array.
	 */
    @GwtCompatible
	public byte[] getByteContent() {
		return content.getBytes();
	}

    /**
     * Returns a not-null string.
     */
    public String getTextContent() {
        return content.getText();
    }

    public HttpResponse setContent(HttpContent content) {
        this.content = content;
        return this;
    }

    public String toString() {
        return JsonObject.create()
            .put("url", url)
            .put("statusCode", statusCode)
            .put("contentLength", content.getLength())
            .put("headers", headers,
                key -> Strings.toNotNull(key, "status"), // handle null key in headers
                value -> JsonArray.create().addStrings(value)
            )
            .put("body", getTextContent())
            .toJsonString();
	}
	
}
