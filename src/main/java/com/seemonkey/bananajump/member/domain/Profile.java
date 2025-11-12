package com.seemonkey.bananajump.member.domain;

import java.time.LocalDate;

import com.seemonkey.bananajump.common.exception.CustomException;
import com.seemonkey.bananajump.common.exception.ErrorType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "profile")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Profile {

	@Id
	@Column(name = "member_id")
	private Long memberId;

	@MapsId
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "member_id")
	private Member member;

	@Column(name = "top_record", nullable = false)
	@PositiveOrZero
	private Long topRecord;

	@Column(name = "coin", nullable = false)
	@PositiveOrZero
	private Long coin;

	@Column(name = "last_checkin")
	private LocalDate lastCheckin;

	@Column(name = "checkin_streak", nullable = false)
	@PositiveOrZero
	private Long checkinStreak;

	@Column(name = "sound", nullable = false)
	private boolean sound;

	@PrePersist
	protected void onCreate() {
		this.coin = 0L;
		this.topRecord = 0L;
		this.checkinStreak = 0L;
		this.sound = true;
	}

	public void updateTopRecord(Long newRecord) {
		if (newRecord < 0)
			throw new CustomException(ErrorType.INVALID_RECORD);
		if (newRecord > this.topRecord)
			this.topRecord = newRecord;
	}

	public void addCoin(Long delta) {
		if (delta <= 0)
			throw new CustomException(ErrorType.INVALID_DELTA);
		this.coin += delta;
	}

	public void useCoin(Long amount) {
		if (amount <= 0)
			throw new CustomException(ErrorType.INVALID_DELTA);
		if (this.coin < amount)
			throw new CustomException(ErrorType.INSUFFICIENT_COIN);
		this.coin -= amount;
	}

	public void checkIn() {
		this.lastCheckin = LocalDate.now();
		this.checkinStreak = this.checkinStreak + 1;
	}

	public void setSound(boolean enabled) {
		if (this.sound == enabled)
			return;
		this.sound = enabled;
	}
}
