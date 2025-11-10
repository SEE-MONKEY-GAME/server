package com.seemonkey.bananajump.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.seemonkey.bananajump.member.domain.Member;
import com.seemonkey.bananajump.member.domain.Profile;

import jakarta.persistence.LockModeType;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Member> {

	Profile findByMember_MemberId(long id);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select p from Profile p where p.member.memberId = :memberId")
	Profile findByMemberIdForUpdate(@Param("memberId") Long memberId);
}
