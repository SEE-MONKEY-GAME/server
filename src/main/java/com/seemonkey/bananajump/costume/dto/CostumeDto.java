package com.seemonkey.bananajump.costume.dto;

import com.seemonkey.bananajump.costume.domain.Costume;
import com.seemonkey.bananajump.costume.domain.CostumeType;

public record CostumeDto(
	Long id,
	String name,
	CostumeType type,
	Long cost,
	String code,
	String desc
) {

	public static CostumeDto from(Costume costume) {
		return new CostumeDto(
			costume.getId(),
			costume.getName(),
			costume.getType(),
			costume.getCost(),
			costume.getCode(),
			costume.getDesc()
		);
	}

	public static record GetCostumeListDto(CostumeDto costume, boolean owned) {
		public static CostumeDto.GetCostumeListDto from(Costume costume, boolean owned) {
			return new CostumeDto.GetCostumeListDto(CostumeDto.from(costume), owned);
		}
	}
}
