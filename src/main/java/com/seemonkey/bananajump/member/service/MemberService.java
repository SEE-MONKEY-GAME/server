package com.seemonkey.bananajump.member.service;

import com.seemonkey.bananajump.member.dto.BasicMemberDto;
import com.seemonkey.bananajump.member.dto.DailyCheckinResultResDto;
import com.seemonkey.bananajump.member.dto.DailyCheckinStatusResDto;

public interface MemberService {
	void createUser(String socialId);

	BasicMemberDto getMemberProfile(Long memberId);

	DailyCheckinStatusResDto getStatus(Long memberId);

	DailyCheckinResultResDto doCheckin(Long memberId);
}
