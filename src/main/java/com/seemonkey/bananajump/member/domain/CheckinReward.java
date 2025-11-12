package com.seemonkey.bananajump.member.domain;

import java.util.List;

import com.seemonkey.bananajump.item.domain.Item;
import com.seemonkey.bananajump.item.service.ItemService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CheckinReward {

	FIRST(1, List.of(new CoinReward(30L))),
	SECOND(2, List.of(new CoinReward(40L))),
	THIRD(3, List.of(new CoinReward(50L))),
	FOURTH(4, List.of(new CoinReward(60L))),
	FIFTH(5, List.of(new CoinReward(70L))),
	SIXTH(6, List.of(new CoinReward(80L))),
	SEVENTH(7, List.of(new ItemReward(1L, 1)));

	private final int checkinStreak;
	private final List<Reward> rewards;

	public sealed interface Reward permits CoinReward, ItemReward {
		RewardType getType();
		void apply(Profile profile, ItemService itemService);
	}

	public record CoinReward(Long amount) implements Reward {
		@Override
		public RewardType getType() {
			return RewardType.COIN;
		}

		@Override
		public void apply(Profile profile, ItemService itemService) {
			profile.addCoin(amount);
		}
	}

	public record ItemReward(Long itemId, int quantity) implements Reward {
		@Override
		public RewardType getType() {
			return RewardType.ITEM;
		}

		@Override
		public void apply(Profile profile, ItemService itemService) {
			itemService.addItem(itemId, profile, quantity);
		}
	}
}
