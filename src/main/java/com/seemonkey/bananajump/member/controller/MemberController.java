package com.seemonkey.bananajump.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seemonkey.bananajump.common.response.BaseResponse;
import com.seemonkey.bananajump.common.response.MemberId;
import com.seemonkey.bananajump.member.dto.BasicMemberDto;
import com.seemonkey.bananajump.member.dto.DailyCheckinResultResDto;
import com.seemonkey.bananajump.member.dto.DailyCheckinStatusResDto;
import com.seemonkey.bananajump.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@GetMapping
	public ResponseEntity<BaseResponse<BasicMemberDto>> getMemberProfile(@MemberId Long memberId) {

		return BaseResponse.ok(memberService.getMemberProfile(memberId));
	}

	@GetMapping("/daily-checkin")
	public ResponseEntity<BaseResponse<DailyCheckinStatusResDto>> getDailyCheckin(@MemberId Long memberId) {
		return BaseResponse.ok(memberService.getStatus(memberId));
	}

	@PostMapping("/daily-checkin")
	public ResponseEntity<BaseResponse<DailyCheckinResultResDto>> doDailyCheckin(@MemberId Long memberId) {
		return BaseResponse.ok(memberService.doCheckin(memberId));
	}
}
