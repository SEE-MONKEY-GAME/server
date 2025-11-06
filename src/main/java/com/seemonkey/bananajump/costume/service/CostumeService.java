package com.seemonkey.bananajump.costume.service;

import java.util.List;

import com.seemonkey.bananajump.costume.dto.CostumeDto;

public interface CostumeService {
	List<CostumeDto.GetCostumeListDto> getCostumeList(Long memberId);
}
