package com.seemonkey.bananajump.item.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seemonkey.bananajump.common.exception.CustomException;
import com.seemonkey.bananajump.common.exception.ErrorType;
import com.seemonkey.bananajump.item.domain.Inventory;
import com.seemonkey.bananajump.item.dto.ItemDto;
import com.seemonkey.bananajump.item.repository.InventoryRepository;
import com.seemonkey.bananajump.item.repository.ItemRepository;
import com.seemonkey.bananajump.member.domain.Profile;
import com.seemonkey.bananajump.member.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemServiceImpl implements ItemService {

	private final ProfileRepository profileRepository;
	@Value("${bananajump.item.limits.default-max}")
	private int DEFAULT_MAX_LIMIT;

	private final InventoryRepository inventoryRepository;
	private final ItemRepository itemRepository;

	@Override
	@Transactional(readOnly = true)
	public List<ItemDto.GetItemListResDto> getItemList(Long memberId) {
		return inventoryRepository.findById_MemberId(memberId).stream()
			.map(ItemDto.GetItemListResDto::from)
			.toList();
	}

	@Override
	public void buyItem(Long itemId, Long memberId) {

		Profile profile = profileRepository.findByMember_MemberId(memberId);
		Inventory inventory = inventoryRepository.findById_MemberIdAndId_ItemId(memberId, itemId)
			.orElseThrow(() -> new CustomException(ErrorType.ITEM_NOT_FOUND));
		
		if (inventory.getQuantity() >= DEFAULT_MAX_LIMIT) {
			throw new CustomException(ErrorType.ITEM_MAX_LIMIT);
		}

		profile.useCoin(inventory.getItem().getCost());
		inventory.addItem();

	}

}
