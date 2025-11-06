package com.seemonkey.bananajump.costume.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seemonkey.bananajump.costume.domain.Closet;

public interface ClosetRepository extends JpaRepository<Closet, Long> {

	List<Closet> findByProfileMemberId(Long memberId);
}
