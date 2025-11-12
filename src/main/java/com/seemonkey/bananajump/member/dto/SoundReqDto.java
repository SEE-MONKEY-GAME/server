package com.seemonkey.bananajump.member.dto;

import com.seemonkey.bananajump.member.domain.Profile;

public record SoundReqDto(
	Profile.SoundType type,
	boolean enabled
) {
}
