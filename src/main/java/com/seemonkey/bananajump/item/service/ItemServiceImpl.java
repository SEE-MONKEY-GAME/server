package com.seemonkey.bananajump.item.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seemonkey.bananajump.common.exception.CustomException;
import com.seemonkey.bananajump.common.exception.ErrorType;
import com.seemonkey.bananajump.item.domain.Inventory;
import com.seemonkey.bananajump.item.domain.Item;
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
		// 1) 모든 아이템 마스터
		List<Item> items = itemRepository.findAll();

		// 2) 해당 멤버의 인벤토리(소유중인 것만 있음)
		List<Inventory> inventories = inventoryRepository.findById_MemberId(memberId);

		// 3) itemId -> quantity 매핑
		Map<Long, Long> qtyByItemId = inventories.stream()
			.collect(Collectors.toMap(
				inv -> inv.getId().getItemId(),
				Inventory::getQuantity
			));

		// 4) 모든 아이템 기준으로 수량 합성(없으면 0)
		return items.stream()
			.map(item -> ItemDto.GetItemListResDto.from(
				item,
				qtyByItemId.getOrDefault(item.getId(), 0L)
			))
			.toList();
	}

	@Override
	public void buyItem(Long itemId, Long memberId, int quantity) {

		// 유저 및 아이템 가져오기
		Profile profile = profileRepository.findByMember_MemberId(memberId);
		Item item = itemRepository.findById(itemId)
			.orElseThrow(() -> new CustomException(ErrorType.ITEM_NOT_FOUND));

		// item 개수 체크
		int currentQty = Optional.ofNullable(
			inventoryRepository.findQuantity(memberId, item.getId())
		).orElse(0);

		// 상한 체크: current + 구매수량 > 50이면 에러
		if (currentQty + quantity > DEFAULT_MAX_LIMIT)
			throw new CustomException(ErrorType.ITEM_MAX_LIMIT);

		// 전체 금액 계산 (현재 quantity 는 고정 1)
		Long totalCost = item.getCost() * quantity;
		if (profile.getCoin() < totalCost)
			throw new CustomException(ErrorType.INSUFFICIENT_COIN);

		// 결제 차감
		profile.useCoin(item.getCost());

		// 인벤토리 갱신
		int finalQuantity = inventoryRepository.upsertInventory(memberId, itemId, quantity);

	}

}
