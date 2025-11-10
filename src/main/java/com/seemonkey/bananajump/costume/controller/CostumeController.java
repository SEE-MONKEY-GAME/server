package com.seemonkey.bananajump.costume.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seemonkey.bananajump.common.response.BaseResponse;
import com.seemonkey.bananajump.costume.domain.CostumeType;
import com.seemonkey.bananajump.costume.dto.CostumeDto;
import com.seemonkey.bananajump.costume.dto.EquipReqDto;
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

	@PutMapping("/equipped/{type}")
	public ResponseEntity<Void> equip(@PathVariable CostumeType type, @RequestBody EquipReqDto req) {
		costumeService.setEquipped(1L, type, req.costumeId());
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/equipped/{type}")
	public ResponseEntity<Void> unEquip(@PathVariable CostumeType type) {
		costumeService.unsetEquipped(1L, type);
		return ResponseEntity.noContent().build();
	}


}
