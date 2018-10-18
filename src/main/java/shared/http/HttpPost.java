package shared.http;

import com.google.gwt.core.shared.GwtIncompatible;
import util.shared.delegate.Callback;

public interface HttpPost extends HttpMethod<HttpPost> {

	@GwtIncompatible
	HttpResponse send();

	void send(Callback<HttpResponse> callback);

}
