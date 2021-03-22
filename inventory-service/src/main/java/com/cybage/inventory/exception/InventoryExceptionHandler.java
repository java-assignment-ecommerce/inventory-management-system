package com.cybage.inventory.exception;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

//@Order(Ordered.LOWEST_PRECEDENCE)

@ControllerAdvice
@ResponseBody
public class InventoryExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String errorMessage2 = MessageFormat.format(ErrorConstants.TYPE_MISMATCH_ERROR_MESSAGE,
				ex.getMostSpecificCause().getMessage());

		ErrorMessage message = new ErrorMessage(ErrorConstants.TYPE_MISMATCH_ERROR_CODE, new Date(), errorMessage2,
				request.getDescription(false));
		return new ResponseEntity<Object>(message, HttpStatus.BAD_REQUEST);

	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String errorMessage = null;
		if (ex.getCause() instanceof InvalidFormatException) {

			InvalidFormatException ife = (InvalidFormatException) ex.getCause();
			logger.warn("Handling exception due to bad data ", ife);
//			  errorMessage = ErrorConstants.MESSAGE_NOT_READABLE_ERROR_MESSAGE;
			try {
				errorMessage = MessageFormat.format(ErrorConstants.NOT_READABLE_ERROR_MESSAGE,
						CollectionUtils.isEmpty(ife.getPath()) ? "" : ife.getPath().get(0).getFieldName());
			} catch (Exception e) {
				logger.warn("Exception while constructing error message. Ignoring ", e);
			}

			return formatErrorResponseForHttpMessageNotReadable(errorMessage, request);
		} else if (ex.getCause() instanceof JsonParseException) {

			JsonParseException jpe = (JsonParseException) ex.getCause();

			logger.warn("Handling exception due to bad data ", jpe);
			// String errorMessage2 ;//=
			// ErrorConstants.MESSAGE_NOT_READABLE_ERROR_MESSAGE;

			try {
				errorMessage = MessageFormat.format(ErrorConstants.NOT_READABLE_ERROR_MESSAGE,
						jpe.getProcessor().getCurrentName());

			} catch (IOException e) {
				logger.error("Failed to get the current name for JsonParser processor ", e);
			}

			return formatErrorResponseForHttpMessageNotReadable(errorMessage, request);
		} else {
			return super.handleHttpMessageNotReadable(ex, headers, status, request);
		}
	}

	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<?> resourceNotFoundException(RecordNotFoundException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(ErrorConstants.RECORD_NOT_FOUND_ERROR_CODE, new Date(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidDataException.class)
	public ResponseEntity<?> handleInvalidDataException(InvalidDataException ex, WebRequest request) {

		String errorMessage2 = MessageFormat.format(ErrorConstants.INVALID_DATA_ERROR_MESSAGE, ex.errMap);
		ErrorMessage message = new ErrorMessage(ErrorConstants.INVALID_DATA_ERROR_CODE, new Date(), errorMessage2,
				request.getDescription(false));
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(ErrorConstants.GENERIC_ERROR_CODE, new Date(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ResponseEntity<Object> formatErrorResponseForHttpMessageNotReadable(final String errorMessage,
			WebRequest request) {

		ErrorMessage message = new ErrorMessage(ErrorConstants.NOT_READABLE_ERROR_CODE, new Date(), errorMessage,
				request.getDescription(false));
		return new ResponseEntity<Object>(message, HttpStatus.BAD_REQUEST);

	}

}