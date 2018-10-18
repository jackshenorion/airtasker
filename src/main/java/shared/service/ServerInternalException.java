package shared.service;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ServerInternalException extends RuntimeException implements IsSerializable {

	public ServerInternalException() {
	}

	public ServerInternalException(String message) {
		super(message);
	}

}
