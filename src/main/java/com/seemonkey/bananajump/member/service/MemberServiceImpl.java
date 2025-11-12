package com.seemonkey.bananajump.member.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seemonkey.bananajump.costume.dto.CostumeDto;
import com.seemonkey.bananajump.costume.service.CostumeService;
import com.seemonkey.bananajump.member.domain.Member;
import com.seemonkey.bananajump.member.domain.Profile;
import com.seemonkey.bananajump.member.dto.BasicMemberDto;
import com.seemonkey.bananajump.member.repository.MemberRepository;
import com.seemonkey.bananajump.member.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;
	private final ProfileRepository profileRepository;
	private final CostumeService costumeService;

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
		List<CostumeDto> closetList = costumeService.getEquippedCostumeList(memberId);
		return BasicMemberDto.from(profile, closetList);
	}
}
