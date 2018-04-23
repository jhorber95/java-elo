package com.software.estudialo.exception;

// TODO: Auto-generated Javadoc
/**
 * The Class EmptyCollectionException.
 */
public class EmptyCollectionException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6355526872081235314L;

	/**
	 * Instantiates a new empty collection exception.
	 */
	public EmptyCollectionException() {

	}

	/**
	 * Instantiates a new empty collection exception.
	 *
	 * @param message the message
	 */
	public EmptyCollectionException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new empty collection exception.
	 *
	 * @param cause the cause
	 */
	public EmptyCollectionException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new empty collection exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public EmptyCollectionException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new empty collection exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 * @param enableSuppression the enable suppression
	 * @param writableStackTrace the writable stack trace
	 */
	public EmptyCollectionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
