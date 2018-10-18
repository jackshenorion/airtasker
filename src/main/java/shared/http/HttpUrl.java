package shared.http;

import java.util.Map;

public final class HttpUrl {

    private String template;

    /**
     * A template can contain params. Below are examples:
     *
     *  - domain.com
     *  - domain.com/{id}
     *  - domain.com/{id1}/{id2}
     */
    public HttpUrl(String template) {
        this.template = template;
    }

    public String getTemplate() {
        return template;
    }

    public String resolve(Map<String, String> params) {
        String url = template;
        for (Map.Entry<String, String> param : params.entrySet()) {
            url = url.replace("{" + param.getKey() + "}", param.getValue());
        }

        if (url.contains("{") || url.contains("}")) {
            throw new IllegalArgumentException("Invalid URL: " + url);
        }
        return url;
    }
}
