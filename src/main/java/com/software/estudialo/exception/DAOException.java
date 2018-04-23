/**
 * 
 */
package com.software.estudialo.exception;

// TODO: Auto-generated Javadoc
/**
 * The Class DAOException.
 *
 * @author LUIS
 */
public class DAOException extends RuntimeException{
	
	/**
	 * Instantiates a new DAO exception.
	 */
	public DAOException(){
		
	}
	
	/**
	 * Instantiates a new DAO exception.
	 *
	 * @param message the message
	 */
	public DAOException(String message){
		super(message);
	}
	
	/**
	 * Instantiates a new DAO exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new DAO exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 * @param enableSuppression the enable suppression
	 * @param writableStackTrace the writable stack trace
	 */
	public DAOException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}
