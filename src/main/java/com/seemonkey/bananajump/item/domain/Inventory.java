package com.seemonkey.bananajump.item.domain;

import com.seemonkey.bananajump.common.exception.CustomException;
import com.seemonkey.bananajump.common.exception.ErrorType;
import com.seemonkey.bananajump.member.domain.Profile;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Inventory {

	@EmbeddedId
	private InventoryId id;

	@MapsId("memberId")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "member_id")
	private Profile profile;

	@MapsId("itemId")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "item_id")
	private Item item;

	@Column(name = "quantity", nullable = false)
	private Long quantity;

	public void useItem() {
		if (this.quantity <= 0)
			throw new CustomException(ErrorType.INSUFFICIENT_ITEM_QUANTITY);
		this.quantity -= 1;
	}

}
