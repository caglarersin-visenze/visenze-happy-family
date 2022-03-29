package com.visenze.happyfamily.exception.handler;

import java.util.Date;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.visenze.happyfamily.exception.ErrorResponse;
import com.visenze.happyfamily.exception.NoProductFoundException;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorResponse errorResponse = ErrorResponse.builder().statusCode(status.value()).date(new Date())
				.errorMessage(status.getReasonPhrase())
				.errorExplanation(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage()).build();
		return super.handleExceptionInternal(ex, errorResponse, headers, status, request);
	}
	
	//HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
	@ExceptionHandler(value = { Exception.class })
	protected ResponseEntity<Object> handleUnexpectedException(Exception ex, WebRequest request) {
		ErrorResponse errorResponse = ErrorResponse.builder().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.date(new Date()).errorMessage("Something went wrong!")
				.errorExplanation(ex.getMessage()).build();
		return super.handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR,
				request);
	}

	@ExceptionHandler(value = { NoProductFoundException.class })
	protected ResponseEntity<Object> handleNoProductFoundException(NoProductFoundException ex, WebRequest request) {
		ErrorResponse errorResponse = ErrorResponse.builder().statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
				.date(new Date()).errorMessage("Wrong Product Information!").errorExplanation(ex.getMessage()).build();
		return super.handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY,
				request);
	}

}
