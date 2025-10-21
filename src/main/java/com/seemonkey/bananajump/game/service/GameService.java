package com.seemonkey.bananajump.game.service;

import com.seemonkey.bananajump.game.dto.SendResultReqDto;

public interface GameService {
	void sendResult(SendResultReqDto dto, Long memberId);
}
