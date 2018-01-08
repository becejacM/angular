package org.levi9.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class MyNotFoundException extends RuntimeException {

	public MyNotFoundException() {
		super();
	}

	public MyNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MyNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public MyNotFoundException(Throwable cause) {
		super(cause);
	}

	public MyNotFoundException(String message) {
		super(message);
	}
}
