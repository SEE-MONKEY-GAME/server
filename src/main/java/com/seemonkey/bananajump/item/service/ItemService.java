package com.seemonkey.bananajump.item.service;

import java.util.List;

import com.seemonkey.bananajump.item.dto.ItemDto;

public interface ItemService {
	List<ItemDto.GetItemListResDto> getItemList(Long memberId);

	void buyItem(Long itemId, Long memberId);
}
