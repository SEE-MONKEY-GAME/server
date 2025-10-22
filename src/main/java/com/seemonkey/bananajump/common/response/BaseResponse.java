package com.seemonkey.bananajump.common.response;

import java.time.OffsetDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.seemonkey.bananajump.common.exception.ErrorType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {
	private int status;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String code;          // "OK", "VALIDATION_ERROR", "NOT_FOUND" ...
	private String message;       // 사용자 메시지
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T data;
	private OffsetDateTime timestamp;
	private String traceId;

	public static <T> ResponseEntity<BaseResponse<T>> ok(T data) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.<T>builder()
				.status(HttpStatus.OK.value())
				.code(HttpStatus.OK.name())
				.data(data)
				.timestamp(OffsetDateTime.now())
				.build());
	}

	public static <T> ResponseEntity<BaseResponse<T>> ok() {
		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.<T>builder()
				.status(HttpStatus.OK.value())
				.code(HttpStatus.OK.name())
				.timestamp(OffsetDateTime.now())
				.build());
	}

	public static <T> ResponseEntity<BaseResponse<T>> created(T data) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(BaseResponse.<T>builder()
				.status(HttpStatus.CREATED.value())
				.code(HttpStatus.CREATED.name())
				.data(data)
				.timestamp(OffsetDateTime.now())
				.build());
	}

	public static <T> ResponseEntity<BaseResponse<T>> created() {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(BaseResponse.<T>builder()
				.status(HttpStatus.CREATED.value())
				.code(HttpStatus.CREATED.name())
				.timestamp(OffsetDateTime.now())
				.build());
	}

	// 에러 응답 생성 메서드
	public static ResponseEntity<BaseResponse<Void>> error(ErrorType errorType) {
		return ResponseEntity.status(errorType.getStatus())
			.body(BaseResponse.<Void>builder()
				.status(errorType.getStatus().value())
				.code(errorType.getErrorCode())
				.message(errorType.getMessage())
				.timestamp(OffsetDateTime.now())
				.build());
	}

	public static <String> ResponseEntity<BaseResponse<String>> error(ErrorType errorType, String msg) {
		return ResponseEntity.status(errorType.getStatus())
			.body(BaseResponse.<String>builder()
				.status(errorType.getStatus().value())
				.code(errorType.getErrorCode())
				.message(errorType.getMessage())
				.data(msg)
				.timestamp(OffsetDateTime.now())
				.build());
	}
}