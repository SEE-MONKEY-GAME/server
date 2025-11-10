package com.seemonkey.bananajump.costume.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seemonkey.bananajump.common.response.BaseResponse;
import com.seemonkey.bananajump.costume.dto.CostumeDto;
import com.seemonkey.bananajump.costume.service.CostumeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/costume")
@RequiredArgsConstructor
public class CostumeController {

	private final CostumeService costumeService;

	@GetMapping
	public ResponseEntity<BaseResponse<List<CostumeDto.GetCostumeListDto>>> getCostumeList() {
		return BaseResponse.ok(costumeService.getCostumeList(1L));
	}

	@PostMapping("/{costumeId}")
	public ResponseEntity<BaseResponse<Void>> buyCostume(@PathVariable Long costumeId) {
		costumeService.buyCostume(costumeId, 1L);
		return BaseResponse.ok();
	}

	@PatchMapping("/{costumeId}")
	public ResponseEntity<BaseResponse<Void>> equipCostume(@PathVariable Long costumeId) {
		costumeService.equipCostume(costumeId, 1L);
		return BaseResponse.ok();
	}

}
