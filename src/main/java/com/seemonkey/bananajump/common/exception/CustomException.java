package com.seemonkey.bananajump.common.exception;

import lombok.Getter;


@Getter
public class CustomException extends RuntimeException{
	private final ErrorType errorType;

	public CustomException(ErrorType errorType) {
		super(errorType.getMessage());
		this.errorType = errorType;
	}
}