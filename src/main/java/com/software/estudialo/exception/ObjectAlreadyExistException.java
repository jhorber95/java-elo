package com.software.estudialo.exception;


// TODO: Auto-generated Javadoc
/**
 * The Class ObjectAlreadyExistException.
 */
public class ObjectAlreadyExistException extends RuntimeException {
	
	/**
	 * Instantiates a new object already exist exception.
	 */
	public ObjectAlreadyExistException() {

	}

	/**
	 * Instantiates a new object already exist exception.
	 *
	 * @param message the message
	 */
	public ObjectAlreadyExistException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new object already exist exception.
	 *
	 * @param cause the cause
	 */
	public ObjectAlreadyExistException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new object already exist exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public ObjectAlreadyExistException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new object already exist exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 * @param enableSuppression the enable suppression
	 * @param writableStackTrace the writable stack trace
	 */
	public ObjectAlreadyExistException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
