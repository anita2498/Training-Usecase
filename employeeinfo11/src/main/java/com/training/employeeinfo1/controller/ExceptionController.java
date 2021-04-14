package com.training.employeeinfo1.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.training.employeeinfo1.exception.NotFoundException;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ NotFoundException.class })
	protected ResponseEntity<Object> handleTaskNotFound(Exception ex, WebRequest request) {
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	/*
	 * @ExceptionHandler({ InvalidDataException.class }) public
	 * ResponseEntity<Object> handleInvalidData(Exception ex, WebRequest request) {
	 * return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(),
	 * HttpStatus.valueOf(422), request); }
	 */

}
