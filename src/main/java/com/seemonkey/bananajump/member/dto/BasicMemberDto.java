package com.seemonkey.bananajump.member.dto;

import java.time.LocalDate;
import java.util.List;

import com.seemonkey.bananajump.costume.dto.CostumeDto;
import com.seemonkey.bananajump.member.domain.Profile;

public record BasicMemberDto(
	Long memberId,
	Long coin,
	Long checkinStreak,
	Boolean todayCheckIn,
	Long topRecord,
	boolean bgmSound,
	boolean effectSound,
	List<CostumeDto> equipment
) {
	public static BasicMemberDto from(Profile profile, List<CostumeDto> equipment) {
		Boolean todayCheckIn = profile.getLastCheckin() != null && !profile.getLastCheckin().isBefore(LocalDate.now());
		return new BasicMemberDto(profile.getMemberId(), profile.getCoin(), profile.getCheckinStreak(), todayCheckIn,
			profile.getTopRecord(), profile.isBgmSound(), profile.isEffectSound(), equipment);
	}
}
