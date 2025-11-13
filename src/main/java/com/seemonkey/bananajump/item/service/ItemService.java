package com.seemonkey.bananajump.item.service;

import java.util.List;

import com.seemonkey.bananajump.item.domain.Item;
import com.seemonkey.bananajump.item.dto.ItemDto;
import com.seemonkey.bananajump.member.domain.Profile;

public interface ItemService {
	List<ItemDto.GetItemListResDto> getItemList(Long memberId);

	void buyItem(Long itemId, Long memberId, int quantity);

	void useItem(Long itemId, Long memberId);

	void addItem(Long itemId, Profile profile, int quantity, boolean ignoreMaxLimit);
}
