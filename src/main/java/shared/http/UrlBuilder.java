package shared.http;

public final class UrlBuilder {

    private StringBuffer buff;

    public UrlBuilder() {
        buff = new StringBuffer();
    }

    public UrlBuilder(String prefix) {
        buff = new StringBuffer(prefix);
    }

    public UrlBuilder append(String uri) {
        int size = buff.length();
        if (size > 0 && buff.charAt(size - 1) == '/') {
            buff.setLength(size - 1);
        }
        if (size > 0 && !uri.startsWith("/")) {
            buff.append("/" + uri);
        }
        else {
            buff.append(uri);
        }
        return this;
    }

    public String build() {
        return buff.toString();
    }

    @Override
    public String toString() {
        return build();
    }

    public static String join(String ... urlSegments) {
        UrlBuilder builder = new UrlBuilder();
        for (String urlSegment : urlSegments) {
            builder.append(urlSegment);
        }
        return builder.build();
    }

}
