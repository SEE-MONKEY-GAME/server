package com.seemonkey.bananajump.member.dto;

import java.time.LocalDate;

public record DailyCheckinResultResDto(
	boolean checkedToday,
	LocalDate lastCheckin,
	long checkinStreak,
	LocalDate today
) {
	public static DailyCheckinResultResDto from(DailyCheckinStatusResDto s) {
		return new DailyCheckinResultResDto(s.checkedToday(), s.lastCheckin(), s.checkinStreak(), s.today());
	}
}