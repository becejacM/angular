package org.levi9.exception;

import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { MyQuantityNotZeroException.class })
	protected ResponseEntity<Object> quantitynotZeroHandle(RuntimeException ex, WebRequest request) {
		String bodyOfResponse = ex.getMessage();
		bodyOfResponse = Optional.ofNullable(bodyOfResponse).orElse("Quantity not zero");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);
	}

	@ExceptionHandler(value = { MyValidationFormException.class })
	protected ResponseEntity<Object> notValidFormParams(RuntimeException ex, WebRequest request) {
		String bodyOfResponse = ex.getMessage();
		bodyOfResponse = Optional.ofNullable(bodyOfResponse).orElse("Validation error in form");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);
	}

	@ExceptionHandler(value = { MyNotFoundException.class })
	protected ResponseEntity<Object> notFoundHandle(RuntimeException ex, WebRequest request) {
		String bodyOfResponse = ex.getMessage();
		bodyOfResponse = Optional.ofNullable(bodyOfResponse).orElse("Comments not found");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(value = { MyAlreadyExistsException.class })
	protected ResponseEntity<Object> alreadyExistsHangle(RuntimeException ex, WebRequest request) {
		String bodyOfResponse = ex.getMessage();
		bodyOfResponse = Optional.ofNullable(bodyOfResponse).orElse("Already exists in database");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);
	}

	@ExceptionHandler(value = { MySavingInRepoException.class })
	protected ResponseEntity<Object> repoFailureHandle(RuntimeException ex, WebRequest request) {
		String bodyOfResponse = ex.getMessage();
		bodyOfResponse = Optional.ofNullable(bodyOfResponse).orElse("Saving in repository failed");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.EXPECTATION_FAILED, request);
	}

}
