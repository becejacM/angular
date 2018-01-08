package org.levi9.exception;

public class MyValidationFormException extends RuntimeException {

	public MyValidationFormException() {
		super();
	}

	public MyValidationFormException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MyValidationFormException(String message, Throwable cause) {
		super(message, cause);
	}

	public MyValidationFormException(String message) {
		super(message);
	}

	public MyValidationFormException(Throwable cause) {
		super(cause);
	}
}
