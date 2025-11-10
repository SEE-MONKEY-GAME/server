package com.seemonkey.bananajump.costume.domain;

import com.seemonkey.bananajump.member.domain.Profile;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "equipped_costume")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EquippedCostume {

	@EmbeddedId
	private EquippedCostumeId id;

	@MapsId("memberId")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "member_id")
	private Profile profile;

	@MapsId("closetId")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "closet_id")
	private Closet closet;

	@Enumerated(EnumType.STRING)
	@Column(name = "costume_type", nullable = false)
	private CostumeType costumeType;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "costume_id")
	private Costume costume;

	public static EquippedCostume from(Profile profile, Closet closet) {
		return EquippedCostume.builder()
			.profile(profile)
			.closet(closet)
			.costumeType(closet.getCostume().getType())
			.costume(closet.getCostume())
			.build();
	}
}