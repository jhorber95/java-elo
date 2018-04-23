/**
 * 
 */
package com.software.estudialo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


// TODO: Auto-generated Javadoc
/**
 * The Class ExceptionHandlingController.
 *
 * @author LUIS
 */
@RestControllerAdvice
public class ExceptionHandlingController {

	/**
	 * Handle object not found.
	 *
	 * @param onf the onf
	 * @return the response entity
	 */
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleObjectNotFound(ObjectNotFoundException onf) {
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorCode("Not Found");
        response.setErrorMessage(onf.getMessage());
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.NOT_FOUND);
	}

	/**
	 * Handle object already exist.
	 *
	 * @param oae the oae
	 * @return the response entity
	 */
	@ExceptionHandler(ObjectAlreadyExistException.class)
	public ResponseEntity<ExceptionResponse> handleObjectAlreadyExist(ObjectAlreadyExistException oae) {
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorCode("Already Exist");
        response.setErrorMessage(oae.getMessage());
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.CONFLICT);
	}

	/**
	 * Handle empty collection.
	 *
	 * @param ece the ece
	 * @return the response entity
	 */
	@ExceptionHandler(EmptyCollectionException.class)
	public ResponseEntity<ExceptionResponse> handleEmptyCollection(EmptyCollectionException ece) {
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorCode("Empty Collection");
        response.setErrorMessage(ece.getMessage());
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.NO_CONTENT);
	}

	/**
	 * Handle value not permitted.
	 *
	 * @param vnpe the vnpe
	 * @return the response entity
	 */
	@ExceptionHandler(ValueNotPermittedException.class)
	public ResponseEntity<ExceptionResponse> handleValueNotPermitted(ValueNotPermittedException vnpe) {
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorCode("Value Not Permitted");
        response.setErrorMessage(vnpe.getMessage());
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handle DAO exception.
	 *
	 * @param daoe the daoe
	 * @return the response entity
	 */
	@ExceptionHandler(DAOException.class)
	public ResponseEntity<ExceptionResponse> handleDAOException(DAOException daoe) {
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorCode("Server Error");
        response.setErrorMessage(daoe.getMessage());
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
