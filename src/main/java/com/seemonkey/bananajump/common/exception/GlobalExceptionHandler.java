package com.seemonkey.bananajump.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.seemonkey.bananajump.common.response.BaseResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<BaseResponse<Void>> handleException(CustomException ex) {
		return BaseResponse.error(ex.getErrorType());
	}

	@ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
	public ResponseEntity<BaseResponse<String>> handleValidation(Exception e) {
		return BaseResponse.error(ErrorType.INVALID_FIELD, e.getMessage());
	}

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<BaseResponse<Void>> handleNoResourceFound(NoResourceFoundException e) {
		return BaseResponse.error(ErrorType.RESOURCE_NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<BaseResponse<String>> handleEtc(Exception e) {
		return BaseResponse.error(ErrorType.INTERNAL_ERROR, e.getMessage());
	}
}