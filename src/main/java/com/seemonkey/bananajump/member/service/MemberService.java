package com.seemonkey.bananajump.member.service;

import com.seemonkey.bananajump.member.dto.BasicMemberDto;

public interface MemberService {
	void createUser(Long socialId);

	BasicMemberDto getMemberProfile(Long memberId);
}
