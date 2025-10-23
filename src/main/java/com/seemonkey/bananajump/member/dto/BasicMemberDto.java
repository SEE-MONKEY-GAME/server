package com.seemonkey.bananajump.member.dto;

import java.time.LocalDate;

import com.seemonkey.bananajump.member.domain.Profile;

public record BasicMemberDto(
	Long memberId,
	Long coin,
	Long checkinStreak,
	Boolean todayCheckIn,
	Long topRecord
) {
	public static BasicMemberDto from(Profile profile) {
		Boolean todayCheckIn = profile.getLastCheckin() != null && !profile.getLastCheckin().isBefore(LocalDate.now());
		return new BasicMemberDto(profile.getMemberId(), profile.getCoin(), profile.getCheckinStreak(), todayCheckIn,
			profile.getTopRecord());
	}
}
