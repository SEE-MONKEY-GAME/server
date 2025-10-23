package com.seemonkey.bananajump.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seemonkey.bananajump.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
