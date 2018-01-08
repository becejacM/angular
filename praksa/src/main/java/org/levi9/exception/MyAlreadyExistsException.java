package org.levi9.exception;

public class MyAlreadyExistsException extends RuntimeException {

	public MyAlreadyExistsException() {
		super();
	}

	public MyAlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MyAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public MyAlreadyExistsException(String message) {
		super(message);
	}

	public MyAlreadyExistsException(Throwable cause) {
		super(cause);
	}

}
