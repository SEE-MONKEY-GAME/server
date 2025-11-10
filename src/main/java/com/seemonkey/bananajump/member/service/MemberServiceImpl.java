package com.seemonkey.bananajump.member.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seemonkey.bananajump.common.exception.CustomException;
import com.seemonkey.bananajump.common.exception.ErrorType;
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

	private final MemberRepository memberRepository;
	private final ProfileRepository profileRepository;

	@Override
	public void createUser(Long socialId) {

		// member Entity
		Member member = Member.builder()
			.socialId(socialId)
			.build();

		memberRepository.save(member);

		// profile Entity
		Profile profile = Profile.builder()
			.member(member)
			.build();

		profileRepository.save(profile);

	}

	@Override
	public BasicMemberDto getMemberProfile(Long memberId) {
		Profile profile = profileRepository.findByMember_MemberId(memberId);

		return BasicMemberDto.from(profile);
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
		// 동시성 안전을 위해 잠금 버전 사용 (권장)
		Profile profile = profileRepository.findByMemberIdForUpdate(memberId);
		LocalDate today = LocalDate.now();
		LocalDate last = profile.getLastCheckin();

		// 이미 오늘 체크인 했다면 예외
		if (last != null && last.isEqual(today)) {
			throw new CustomException(ErrorType.ALREADY_CHECKED_IN);
		}

		profile.checkIn();

		// (옵션) 보상 코인 지급이 필요하면 여기서 profile.addCoin(amount) 호출
		// profile.addCoin(10L);

		return DailyCheckinResultResDto.from(getStatus(memberId));
	}

}
