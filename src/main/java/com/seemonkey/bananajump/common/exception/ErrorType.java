package com.seemonkey.bananajump.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {

	// global 에러
	INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-999", "알 수 없는 서버 오류가 발생했습니다."),
	INVALID_FIELD(HttpStatus.BAD_REQUEST, "COMMON-001", "유효성 검사 실패. 상세: "),
	RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON-002", "요청한 정보를 찾을 수 없습니다."),
	;

	private final HttpStatus status;
	private final String errorCode;
	private final String message;
}