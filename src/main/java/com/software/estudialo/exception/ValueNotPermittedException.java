package com.software.estudialo.exception;

// TODO: Auto-generated Javadoc
/**
 * The Class ValueNotPermittedException.
 */
public class ValueNotPermittedException extends RuntimeException {	


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7949435273202374202L;

	/**
	 * Instantiates a new value not permitted exception.
	 */
	public ValueNotPermittedException() {

	}

	/**
	 * Instantiates a new value not permitted exception.
	 *
	 * @param message the message
	 */
	public ValueNotPermittedException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new value not permitted exception.
	 *
	 * @param cause the cause
	 */
	public ValueNotPermittedException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new value not permitted exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public ValueNotPermittedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new value not permitted exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 * @param enableSuppression the enable suppression
	 * @param writableStackTrace the writable stack trace
	 */
	public ValueNotPermittedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
