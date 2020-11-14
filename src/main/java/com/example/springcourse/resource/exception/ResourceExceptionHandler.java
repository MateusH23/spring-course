package com.example.springcourse.resource.exception;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.springcourse.exception.NotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ApiError> notFoundExceptionHandler(NotFoundException ex) {
		ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage(), new Date());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiError> badCredentialsExceptionHandler(BadCredentialsException ex) {
		ApiError error = new ApiError(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), new Date());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ApiError> accessDeniedExceptionHandler(AccessDeniedException ex) {
		ApiError apiError = new ApiError(HttpStatus.FORBIDDEN.value(), ex.getMessage(), new Date());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiError);
	}
	
	//MaxUploadSizeExceededException
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<ApiError> maxUploadSizeExceededExceptionHandler(MaxUploadSizeExceededException ex) {
		String parts[] = ex.getMessage().split(":");
		String msg = parts[parts.length - 1].trim().toUpperCase();
		
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), msg, new Date());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String defaultMessage = "Invalid field(s)";
		List<String> errors = new ArrayList<>();
		
		//errors = ex.getBindingResult().getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.toList()); // Stream
		ex.getBindingResult().getAllErrors().forEach(error -> {
			errors.add(error.getDefaultMessage());
		});
		
		ApiErrorList error = new ApiErrorList(HttpStatus.BAD_REQUEST.value(), defaultMessage, new Date(), errors);
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
}
