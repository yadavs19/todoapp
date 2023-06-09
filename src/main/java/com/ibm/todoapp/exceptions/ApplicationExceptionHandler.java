package com.ibm.todoapp.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ibm.todoapp.dto.ErrorResponse;

@RestControllerAdvice
public class ApplicationExceptionHandler {
	
	// MethodArgumentNotValidException
	 @ExceptionHandler(MethodArgumentNotValidException.class)
	    @ResponseStatus(HttpStatus.BAD_REQUEST)
	    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
	        Map<String, String> errors = new HashMap<>();
	        ex.getBindingResult().getAllErrors().forEach((error) -> {
	            String fieldName = ((FieldError) error).getField();
	            String errorMessage = error.getDefaultMessage();
	            errors.put(fieldName, errorMessage);
	        });

	        return ResponseEntity.badRequest().body(errors);
	    }

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFoundException(UserNotFoundException ex) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorMessage(ex.getMessage());
		errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
		errorResponse.setTimestamp(LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}
	
	@ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(UnAuthorizedException ex) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorMessage(ex.getMessage());
		errorResponse.setStatus(HttpStatus.FORBIDDEN.value());
		errorResponse.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }
	
	@ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(TodoNotFoundException ex) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorMessage(ex.getMessage());
		errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
		errorResponse.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
