package com.seemonkey.bananajump.costume.domain;

import java.time.OffsetDateTime;

import org.springframework.data.annotation.CreatedDate;

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
@Table(name = "closet")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Closet {

	@EmbeddedId
	private ClosetId id;

	@MapsId("memberId")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "member_id")
	private Profile profile;

	@MapsId("costumeId")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "costume_id")
	private Costume costume;

	@CreatedDate
	@Column(name = "created_at", nullable = false, updatable = false)
	private OffsetDateTime createdAt;
}
