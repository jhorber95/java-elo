package com.software.estudialo.exception;


// TODO: Auto-generated Javadoc
/**
 * The Class ObjectNotFoundException.
 */
public class ObjectNotFoundException extends RuntimeException {
	
	/**
	 * Instantiates a new object not found exception.
	 */
	public ObjectNotFoundException() {

	}

	/**
	 * Instantiates a new object not found exception.
	 *
	 * @param message the message
	 */
	public ObjectNotFoundException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new object not found exception.
	 *
	 * @param cause the cause
	 */
	public ObjectNotFoundException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new object not found exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public ObjectNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new object not found exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 * @param enableSuppression the enable suppression
	 * @param writableStackTrace the writable stack trace
	 */
	public ObjectNotFoundException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
