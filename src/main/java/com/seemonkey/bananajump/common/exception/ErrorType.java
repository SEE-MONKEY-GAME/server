package com.seemonkey.bananajump.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {

	// ===============================
	// [공통] Global / Validation
	// ===============================
	INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-999", "알 수 없는 서버 오류가 발생했습니다."),
	INVALID_FIELD(HttpStatus.BAD_REQUEST, "COMMON-001", "유효성 검사 실패. 상세: "),
	RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON-002", "요청한 정보를 찾을 수 없습니다."),
	METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "COMMON-003", "지원하지 않는 HTTP 메서드입니다."),
	INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "COMMON-004", "요청 파라미터가 올바르지 않습니다."),
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON-005", "인증이 필요합니다."),
	FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON-006", "접근 권한이 없습니다."),

	// ===============================
	// [Member]
	// ===============================
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER-001", "회원 정보를 찾을 수 없습니다."),
	MEMBER_ALREADY_DELETED(HttpStatus.BAD_REQUEST, "MEMBER-002", "이미 탈퇴한 회원입니다."),
	INVALID_SOCIAL_TOKEN(HttpStatus.UNAUTHORIZED, "MEMBER-003", "유효하지 않은 소셜 토큰입니다."),

	// ===============================
	// [Profile / Record]
	// ===============================
	INVALID_RECORD(HttpStatus.BAD_REQUEST, "RECORD-001", "record 값은 0 이상이어야 합니다."),

	// ===============================
	// [Banana / 재화 관련]
	// ===============================
	INVALID_DELTA(HttpStatus.BAD_REQUEST, "BANANA-001", "banana 증감값은 0보다 커야 합니다."),
	INSUFFICIENT_BANANA(HttpStatus.BAD_REQUEST, "BANANA-002", "보유 바나나가 부족합니다."),

	// ===============================
	// [Item / 상점]
	// ===============================
	ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "ITEM-001", "아이템을 찾을 수 없습니다."),
	ITEM_ALREADY_OWNED(HttpStatus.BAD_REQUEST, "ITEM-002", "이미 보유 중인 아이템입니다."),

	// ===============================
	// [Costume / 장착 아이템]
	// ===============================
	COSTUME_NOT_FOUND(HttpStatus.NOT_FOUND, "COSTUME-001", "코스튬을 찾을 수 없습니다."),
	INVALID_COSTUME_TYPE(HttpStatus.BAD_REQUEST, "COSTUME-002", "유효하지 않은 코스튬 타입입니다."),

	// ===============================
	// [Attendance / 출석]
	// ===============================
	ALREADY_CHECKED_IN(HttpStatus.BAD_REQUEST, "ATTENDANCE-001", "오늘은 이미 출석했습니다."),
	INVALID_CHECKIN_DATE(HttpStatus.BAD_REQUEST, "ATTENDANCE-002", "잘못된 출석 일자입니다."),

	;

	private final HttpStatus status;
	private final String errorCode;
	private final String message;
}