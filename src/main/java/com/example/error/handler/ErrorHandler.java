package com.example.error.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.domain.ApiError;
import com.example.exception.ResourceNotFoundException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	protected ResponseEntity<ApiError> handleEntityNotFound(ResourceNotFoundException ex) {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex);
		return new ResponseEntity<ApiError>(apiError, apiError.getStatus());
	}
	
	@ExceptionHandler(RuntimeException.class)
	protected ResponseEntity<ApiError> handleEntityNotFound(RuntimeException ex) {
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex);
		return new ResponseEntity<ApiError>(apiError, apiError.getStatus());
	}

}