package org.levi9.exception;

public class MySavingInRepoException extends RuntimeException {

	public MySavingInRepoException() {
		super();
	}

	public MySavingInRepoException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MySavingInRepoException(String message, Throwable cause) {
		super(message, cause);
	}

	public MySavingInRepoException(String message) {
		super(message);
	}

	public MySavingInRepoException(Throwable cause) {
		super(cause);
	}

}
