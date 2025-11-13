package com.seemonkey.bananajump.member.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seemonkey.bananajump.common.exception.CustomException;
import com.seemonkey.bananajump.common.exception.ErrorType;
import com.seemonkey.bananajump.item.service.ItemService;
import com.seemonkey.bananajump.member.domain.CheckinReward;
import com.seemonkey.bananajump.costume.dto.CostumeDto;
import com.seemonkey.bananajump.costume.service.CostumeService;
import com.seemonkey.bananajump.member.domain.Member;
import com.seemonkey.bananajump.member.domain.Profile;
import com.seemonkey.bananajump.member.dto.BasicMemberDto;
import com.seemonkey.bananajump.member.dto.DailyCheckinResultResDto;
import com.seemonkey.bananajump.member.dto.DailyCheckinStatusResDto;
import com.seemonkey.bananajump.member.dto.SoundReqDto;
import com.seemonkey.bananajump.member.repository.MemberRepository;
import com.seemonkey.bananajump.member.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

	private final ItemService itemService;
	private final MemberRepository memberRepository;
	private final ProfileRepository profileRepository;
	private final CostumeService costumeService;


	@Override
	public Member getOrCreateMemberBySocialId(String token) {
		return memberRepository.findBySocialId(token).orElseGet(
			() -> createMember(token)
		);
	}

	@Override
	public void setSound(Long memberId, SoundReqDto req) {
		Profile profile = getProfile(memberId);
		profile.setBgmSound(req.type(), req.enabled());
	}

	private Member createMember(String token) {
		Member member = Member.of(token);

		memberRepository.save(member);
		profileRepository.save(Profile.builder().member(member).build());
		return member;
	}

	@Override
	public BasicMemberDto getMemberProfile(Long memberId) {
		Profile profile = getProfile(memberId);
		List<CostumeDto> closetList = costumeService.getEquippedCostumeList(memberId);
		return BasicMemberDto.from(profile, closetList);
	}

	@Transactional(readOnly = true)
	@Override
	public DailyCheckinStatusResDto getStatus(Long memberId) {
		Profile profile = getProfile(memberId);
		LocalDate today = LocalDate.now();
		LocalDate last = profile.getLastCheckin();

		long streak = calculateEffectiveStreak(profile, today);
		boolean checkedToday = last != null && last.isEqual(today);

		return new DailyCheckinStatusResDto(
			checkedToday,
			last,
			streak,
			today
		);
	}

	private long calculateEffectiveStreak(Profile profile, LocalDate today) {
		LocalDate last = profile.getLastCheckin();
		long streak = profile.getCheckinStreak();

		if (last == null) {
			return 0;
		}
		if (last.isEqual(today)) {
			// 이미 오늘 출석한 상태 → 그대로
			return streak;
		}
		if (last.isEqual(today.minusDays(1)) && streak < 7) {
			// 어제 출석했고, 아직 7일 미만 → 유지
			return streak;
		}
		// 그 외: 끊긴 것으로 보고 0으로
		return 0;
	}


	@Transactional
	@Override
	public DailyCheckinResultResDto doCheckin(Long memberId) {

		Profile profile = getProfileForUpdate(memberId);
		LocalDate today = LocalDate.now();
		LocalDate last = profile.getLastCheckin();

		// 이미 오늘 체크인 했다면 예외
		if (last != null && last.isEqual(today)) {
			throw new CustomException(ErrorType.ALREADY_CHECKED_IN);
		}

		// 오늘 기준 유효 streak 계산
		long effectiveStreak = calculateEffectiveStreak(profile, today);

		// 7일 루프라면: 1~7 반복
		int newStreak = (int)((effectiveStreak % 7) + 1);

		// 프로필에 반영 (Profile에 메서드 하나 파는 것도 좋음)
		profile.updateCheckin(today, newStreak);

		// 보상 지급
		applyReward(profile, newStreak);

		// 최신 상태 리턴
		return new DailyCheckinResultResDto(
			true,
			today,
			newStreak,
			today
		);
	}


	private void applyReward(Profile profile, int checkinStreak) {

		// streak 일치하는 보상 enum 찾기
		CheckinReward reward = Arrays.stream(CheckinReward.values())
			.filter(r -> r.getCheckinStreak() == checkinStreak)
			.findFirst()
			.orElseThrow(() -> new CustomException(ErrorType.INVALID_CHECKIN_DATE));

		// 각 보상 apply 실행
		try {
			reward.getRewards().forEach(r -> r.apply(profile, itemService));
		} catch (CustomException e) {
			if (e.getErrorType() != ErrorType.ITEM_MAX_LIMIT) {
				throw e;
			}
		}
	}

	private Profile getProfile(Long memberId) {
		return profileRepository.findByMember_MemberId(memberId);
	}

	private Profile getProfileForUpdate(Long memberId) {
		return profileRepository.findByMemberIdForUpdate(memberId)
			.orElseThrow(() -> new CustomException(ErrorType.MEMBER_NOT_FOUND));
	}

}
