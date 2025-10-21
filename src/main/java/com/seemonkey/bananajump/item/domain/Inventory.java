package com.seemonkey.bananajump.item.domain;

import com.seemonkey.bananajump.member.domain.Profile;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inventory")
@Getter @Setter
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

	@PrePersist
	protected void onCreate() {
		this.quantity = 0L;
	}
}
