package org.levi9.exception;

/**
 * @author m.becejac Exceptions for configurations when params are not valid
 *
 */
public class MyNotValidParamsException extends RuntimeException {

	public MyNotValidParamsException() {
		super();
	}

	public MyNotValidParamsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MyNotValidParamsException(String message, Throwable cause) {
		super(message, cause);
	}

	public MyNotValidParamsException(String message) {
		super(message);
	}

	public MyNotValidParamsException(Throwable cause) {
		super(cause);
	}

}
