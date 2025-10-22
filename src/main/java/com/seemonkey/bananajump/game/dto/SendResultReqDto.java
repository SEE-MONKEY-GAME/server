package com.seemonkey.bananajump.game.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@NotNull
public record SendResultReqDto(
	@PositiveOrZero
	Long score,
	@PositiveOrZero
	Long coin

) {
}
