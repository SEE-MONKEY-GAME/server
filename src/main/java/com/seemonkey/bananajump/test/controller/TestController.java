package com.seemonkey.bananajump.test.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seemonkey.bananajump.common.response.BaseResponse;
import com.seemonkey.bananajump.item.dto.ItemDto;
import com.seemonkey.bananajump.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

	private final MemberService memberService;

	@GetMapping("{memberId}")
	public ResponseEntity<BaseResponse<Void>> makeTestMember(@PathVariable Long memberId) {
		memberService.createUser(memberId);
		return BaseResponse.created();
	}

}
