package com.seemonkey.bananajump.item.dto;

import com.seemonkey.bananajump.item.domain.Inventory;
import com.seemonkey.bananajump.item.domain.Item;

public record ItemDto(
	Long id,
	String name,
	Long cost,
	String code,
	String desc
) {
	public static ItemDto from(Item item) {
		return new ItemDto(
			item.getId(),
			item.getName(),
			item.getCost(),
			item.getCode(),
			item.getDesc()
		);
	}

	public static record GetItemListResDto(
		ItemDto item,
		Long quantity
	) {

		public static GetItemListResDto from(Inventory inventory) {
			return new GetItemListResDto(ItemDto.from(inventory.getItem()), inventory.getQuantity());
		}

		public static GetItemListResDto from(Item item, long quantity) {
			return new GetItemListResDto(ItemDto.from(item), quantity);
		}
	}
}
