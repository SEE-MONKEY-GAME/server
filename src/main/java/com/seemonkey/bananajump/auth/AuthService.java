package com.seemonkey.bananajump.auth;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.seemonkey.bananajump.member.domain.Member;
import com.seemonkey.bananajump.member.domain.Profile;
import com.seemonkey.bananajump.member.repository.MemberRepository;
import com.seemonkey.bananajump.member.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final MemberRepository memberRepository;
	private final ProfileRepository profileRepository;

	public void issueToken(String token) {
		Member member = getOrCreateMemberBySocialId(token);

	}

	public Member getOrCreateMemberBySocialId(String token) {
		return memberRepository.findBySocialId(token).orElse(
			createMember(token)
		);
	}

	private Member createMember(String token) {
		Member member = Member.builder()
			.socialId(token)
			.build();

		memberRepository.save(member);
		profileRepository.save(Profile.builder().member(member).build());
		return member;
	}
}

