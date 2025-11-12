package com.seemonkey.bananajump.member.dto;

import java.time.LocalDate;

public record DailyCheckinStatusResDto(
	boolean checkedToday,
	LocalDate lastCheckin,
	long checkinStreak,
	LocalDate today
) {}
