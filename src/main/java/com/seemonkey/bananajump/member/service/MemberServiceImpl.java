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

	@Override
	public DailyCheckinStatusResDto getStatus(Long memberId) {
		Profile profile = getProfile(memberId);
		LocalDate today = LocalDate.now();
		LocalDate last = profile.getLastCheckin();

		// streak 보정용 변수
		long streak = profile.getCheckinStreak();
		boolean checkedToday = false;

		if (last == null) {
			// 첫 출석 전
			streak = 0;
		} else if (last.isEqual(today)) {
			// 이미 오늘 출석 완료
			checkedToday = true;
		} else if (last.isEqual(today.minusDays(1)) && streak < 7) {
			// 어제 출석 → 유지
			// 아무 변화 없음
		} else {
			// 어제 이전이면 streak 리셋
			profile.resetCheckinStreak();
			streak = 0;
		}

		return new DailyCheckinStatusResDto(
			checkedToday,
			last,
			streak,
			today
		);
	}

	@Transactional
	@Override
	public DailyCheckinResultResDto doCheckin(Long memberId) {

		Profile profile = getProfile(memberId);
		LocalDate today = LocalDate.now();
		LocalDate last = profile.getLastCheckin();

		// 이미 오늘 체크인 했다면 예외
		if (last != null && last.isEqual(today))
			throw new CustomException(ErrorType.ALREADY_CHECKED_IN);

		int checkinStreak = profile.checkIn();
		applyReward(profile, checkinStreak);

		return DailyCheckinResultResDto.from(getStatus(memberId));
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

}
