package com.seemonkey.bananajump.costume.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.seemonkey.bananajump.costume.domain.Closet;
import com.seemonkey.bananajump.costume.domain.Costume;
import com.seemonkey.bananajump.costume.dto.CostumeDto;
import com.seemonkey.bananajump.costume.repository.ClosetRepository;
import com.seemonkey.bananajump.costume.repository.CostumeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CostumeServiceImpl implements CostumeService {

	private final CostumeRepository costumeRepository;
	private final ClosetRepository closetRepository;

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

}
