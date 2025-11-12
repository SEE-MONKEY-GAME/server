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

	private Member createMember(String token) {
		Member member = Member.of(token);

		memberRepository.save(member);
		profileRepository.save(Profile.builder().member(member).build());
		return member;
	}

	@Override
	public BasicMemberDto getMemberProfile(Long memberId) {
		Profile profile = profileRepository.findByMember_MemberId(memberId);
		List<CostumeDto> closetList = costumeService.getEquippedCostumeList(memberId);
		return BasicMemberDto.from(profile, closetList);
	}

	@Transactional(readOnly = true)
	@Override
	public DailyCheckinStatusResDto getStatus(Long memberId) {
		Profile profile = profileRepository.findByMember_MemberId(memberId);
		LocalDate today = LocalDate.now();
		LocalDate last = profile.getLastCheckin();
		boolean checked = last != null && last.isEqual(today);

		return new DailyCheckinStatusResDto(
			checked,
			last,
			profile.getCheckinStreak(),
			today
		);
	}

	@Transactional
	@Override
	public DailyCheckinResultResDto doCheckin(Long memberId) {

		Profile profile = profileRepository.findByMember_MemberId(memberId);
		LocalDate today = LocalDate.now();
		LocalDate last = profile.getLastCheckin();

		// 이미 오늘 체크인 했다면 예외
		if (last != null && last.isEqual(today))
			throw new CustomException(ErrorType.ALREADY_CHECKED_IN);

		int checkinStreak = profile.checkIn();
		applyReward(profile, checkinStreak);

		// todo: 토큰 새로 발급

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

}
