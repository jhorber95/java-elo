/**
 * 
 */
package com.software.estudialo.exception;

// TODO: Auto-generated Javadoc
/**
 * The Class ExceptionResponse.
 *
 * @author LUIS
 */
public class ExceptionResponse {
	
	 /** The error code. */
 	private String errorCode;
	    
    	/** The error message. */
    	private String errorMessage;
	 
	    /**
    	 * Instantiates a new exception response.
    	 */
    	public ExceptionResponse() {
	    }
	 
	    /**
    	 * Gets the error code.
    	 *
    	 * @return the error code
    	 */
    	public String getErrorCode() {
	        return errorCode;
	    }
	 
	    /**
    	 * Sets the error code.
    	 *
    	 * @param errorCode the new error code
    	 */
    	public void setErrorCode(String errorCode) {
	        this.errorCode = errorCode;
	    }
	 
	    /**
    	 * Gets the error message.
    	 *
    	 * @return the error message
    	 */
    	public String getErrorMessage() {
	        return errorMessage;
	    }
	 
	    /**
    	 * Sets the error message.
    	 *
    	 * @param errorMessage the new error message
    	 */
    	public void setErrorMessage(String errorMessage) {
	        this.errorMessage = errorMessage;
	    }

}
