package com.seemonkey.bananajump.game.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seemonkey.bananajump.game.domain.Record;
import com.seemonkey.bananajump.game.dto.SendResultReqDto;
import com.seemonkey.bananajump.game.repository.GameRepository;
import com.seemonkey.bananajump.member.domain.Profile;
import com.seemonkey.bananajump.member.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class GameServiceImpl implements GameService {

	private final GameRepository gameRepository;
	private final ProfileRepository profileRepository;

	@Override
	public void sendResult(SendResultReqDto dto, Long memberId) {

		Profile profile = profileRepository.findByMember_MemberId(memberId);

		//todo 1: entity로 변환 후 저장하는 로직
		gameRepository.save(Record.from(dto, profile));

		//todo 2: 바나나 갱신
		profile.addCoin(dto.coin());

		//todo 3: 기록 비교 후 최고기록 갱신
		profile.updateTopRecord(dto.score());
	}
}
