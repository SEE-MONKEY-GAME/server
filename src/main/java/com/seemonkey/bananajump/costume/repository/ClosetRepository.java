package com.seemonkey.bananajump.costume.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seemonkey.bananajump.costume.domain.Closet;
import com.seemonkey.bananajump.costume.domain.Costume;

public interface ClosetRepository extends JpaRepository<Closet, Long> {

	List<Closet> findByProfileMemberId(Long memberId);

	boolean existsByProfileMemberIdAndCostume(Long memberId, Costume costume);
}
