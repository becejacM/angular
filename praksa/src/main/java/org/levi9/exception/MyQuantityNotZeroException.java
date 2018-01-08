package org.levi9.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class MyQuantityNotZeroException extends RuntimeException {

	public MyQuantityNotZeroException() {
		super();
	}

	public MyQuantityNotZeroException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MyQuantityNotZeroException(Throwable cause) {
		super(cause);
	}

	public MyQuantityNotZeroException(String message) {
		super(message);
	}

	public MyQuantityNotZeroException(String message, Throwable root) {
		super(message, root);
	}

}
