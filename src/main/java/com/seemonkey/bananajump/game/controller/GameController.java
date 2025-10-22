package com.seemonkey.bananajump.game.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seemonkey.bananajump.common.response.BaseResponse;
import com.seemonkey.bananajump.game.dto.SendResultReqDto;
import com.seemonkey.bananajump.game.service.GameService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

	private final GameService gameService;

	@PostMapping
	public ResponseEntity<BaseResponse<Void>> sendResult(@RequestBody SendResultReqDto dto) {
		gameService.sendResult(dto, 1L);

		return BaseResponse.created();
	}
}
