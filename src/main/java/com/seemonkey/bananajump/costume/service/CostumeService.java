package com.seemonkey.bananajump.costume.service;

import java.util.List;

import com.seemonkey.bananajump.costume.domain.CostumeType;
import com.seemonkey.bananajump.costume.dto.CostumeDto;

public interface CostumeService {
	List<CostumeDto.GetCostumeListDto> getCostumeList(Long memberId);

	void buyCostume(Long costumeId, Long memberId);

	void setEquipped(Long memberId, CostumeType type, Long costumeId); // equip or replace (idempotent)

	void unsetEquipped(Long memberId, CostumeType type);

}
