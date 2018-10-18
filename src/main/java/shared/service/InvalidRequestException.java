package shared.service;

import com.google.gwt.user.client.rpc.IsSerializable;

public class InvalidRequestException extends RuntimeException implements IsSerializable {

	public InvalidRequestException() {
	}

	public InvalidRequestException(String message) {
		super(message);
	}

}
