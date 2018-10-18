package shared.http;

import com.google.gwt.core.shared.GwtIncompatible;
import util.shared.delegate.Callback;

public interface HttpGet extends HttpMethod<HttpGet> {

	@GwtIncompatible
	HttpResponse send();

	void send(Callback<HttpResponse> callback);

}