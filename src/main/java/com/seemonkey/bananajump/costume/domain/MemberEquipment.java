package com.seemonkey.bananajump.costume.domain;

import com.seemonkey.bananajump.member.domain.Profile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "member_equipment")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MemberEquipment {

	@Id
	@Column(name = "member_id")
	private Long memberId;

	@MapsId
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "member_id")
	private Profile profile;

	@Enumerated(EnumType.STRING)
	@Column(name = "costume_type", nullable = false)
	private CostumeType costumeType;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "costume_id")
	private Costume costume;
}