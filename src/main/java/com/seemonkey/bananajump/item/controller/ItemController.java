package com.seemonkey.bananajump.item.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seemonkey.bananajump.common.response.BaseResponse;
import com.seemonkey.bananajump.common.response.MemberId;
import com.seemonkey.bananajump.item.dto.ItemDto;
import com.seemonkey.bananajump.item.service.ItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

	private final ItemService itemService;

	@GetMapping
	public ResponseEntity<BaseResponse<List<ItemDto.GetItemListResDto>>> getItemList(@MemberId Long memberId) {

		return BaseResponse.ok(itemService.getItemList(memberId));
	}

	@PostMapping("/{itemId}")
	public ResponseEntity<BaseResponse<Void>> buyItem(@PathVariable Long itemId, @MemberId Long memberId) {
		itemService.buyItem(itemId, memberId, 1);
		return BaseResponse.ok();
	}

	@PatchMapping("/{itemId}")
	public ResponseEntity<BaseResponse<Void>> useItem(@PathVariable Long itemId, @MemberId Long memberId) {
		itemService.useItem(itemId, memberId);
		return BaseResponse.ok();
	}
}
