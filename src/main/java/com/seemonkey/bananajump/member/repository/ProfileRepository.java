package com.seemonkey.bananajump.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seemonkey.bananajump.member.domain.Member;
import com.seemonkey.bananajump.member.domain.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Member> {

	Profile findByMember_MemberId(long id);
}
