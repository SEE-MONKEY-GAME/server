package com.seemonkey.bananajump.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seemonkey.bananajump.item.repository.InventoryRepository;
import com.seemonkey.bananajump.item.repository.ItemRepository;
import com.seemonkey.bananajump.member.domain.Member;
import com.seemonkey.bananajump.member.domain.Profile;
import com.seemonkey.bananajump.member.repository.MemberRepository;
import com.seemonkey.bananajump.member.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;
	private final ProfileRepository profileRepository;
	private final ItemRepository itemRepository;
	private final InventoryRepository inventoryRepository;

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
}
