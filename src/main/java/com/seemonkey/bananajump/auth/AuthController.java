package com.seemonkey.bananajump.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seemonkey.bananajump.common.response.BaseResponse;

import lombok.RequiredArgsConstructor;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping
	public ResponseEntity<BaseResponse<Void>> issueToken(@RequestBody String token) {
		authService.issueToken(token);
		return BaseResponse.ok();
	}
}
