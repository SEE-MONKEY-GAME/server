package com.seemonkey.bananajump.costume.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seemonkey.bananajump.common.exception.CustomException;
import com.seemonkey.bananajump.common.exception.ErrorType;
import com.seemonkey.bananajump.costume.domain.Closet;
import com.seemonkey.bananajump.costume.domain.Costume;
import com.seemonkey.bananajump.costume.dto.CostumeDto;
import com.seemonkey.bananajump.costume.repository.ClosetRepository;
import com.seemonkey.bananajump.costume.repository.CostumeRepository;
import com.seemonkey.bananajump.member.domain.Profile;
import com.seemonkey.bananajump.member.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CostumeServiceImpl implements CostumeService {

	private final CostumeRepository costumeRepository;
	private final ClosetRepository closetRepository;
	private final ProfileRepository profileRepository;

	@Override
	public List<CostumeDto.GetCostumeListDto> getCostumeList(Long memberId) {
		// 1) 모든 코스튬
		List<Costume> costumes = costumeRepository.findAll();

		// 2) 해당 멤버의 옷장 (소유한 코스튬 목록)
		List<Closet> closets = closetRepository.findByProfileMemberId(memberId);

		// 3) 소유 중인 costumeId만 따로 set으로 추출
		Set<Long> ownedCostumeIds = closets.stream()
			.map(closet -> closet.getCostume().getId())
			.collect(Collectors.toSet());

		// 4) 모든 코스튬에 대해 소유 여부 매핑
		return costumes.stream()
			.map(costume -> CostumeDto.GetCostumeListDto.from(
				costume,
				ownedCostumeIds.contains(costume.getId())
			))
			.toList();

	}

	@Transactional
	@Override
	public void buyCostume(Long costumeId, Long memberId) {
		// 유저 및 코스튬 가져오기
		Profile profile = profileRepository.findByMember_MemberId(memberId);
		Costume costume = costumeRepository.findById(costumeId)
			.orElseThrow(() -> new CustomException(ErrorType.COSTUME_NOT_FOUND));

		if (closetRepository.existsByProfileMemberIdAndCostume(memberId, costume))
			throw new CustomException(ErrorType.COSTUME_ALREADY_OWNED);

		// 전체 금액 계산
		Long totalCost = costume.getCost();
		if (profile.getCoin() < totalCost)
			throw new CustomException(ErrorType.INSUFFICIENT_COIN);

		// 결제 차감
		profile.useCoin(totalCost);

		// 인벤토리 갱신
		closetRepository.save(Closet.from(costume, profile));

	}

}
