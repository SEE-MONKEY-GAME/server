package com.seemonkey.bananajump.game.domain;

import java.time.OffsetDateTime;

import org.springframework.data.annotation.CreatedDate;

import com.seemonkey.bananajump.member.domain.Member;
import com.seemonkey.bananajump.member.domain.Profile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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

@Entity
@Table(name = "record")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Record {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "record_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "member_id")
	private Profile profile;

	@Column(name = "started_at", nullable = false, updatable = false)
	private OffsetDateTime startedAt;

	@Column(name = "ended_at", nullable = false, updatable = false)
	private OffsetDateTime endedAt;

	@Column(name = "score", nullable = false)
	private int score;

	@Column(name = "banana1", nullable = false)
	private int banana1;

	@Column(name = "banana2", nullable = false)
	private int banana2;

	@Column(name = "banana3", nullable = false)
	private int banana3;

}
