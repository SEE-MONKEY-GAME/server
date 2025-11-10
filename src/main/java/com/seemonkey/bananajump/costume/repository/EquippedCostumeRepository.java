package com.seemonkey.bananajump.costume.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seemonkey.bananajump.costume.domain.CostumeType;
import com.seemonkey.bananajump.costume.domain.EquippedCostume;
import com.seemonkey.bananajump.costume.domain.EquippedCostumeId;

public interface EquippedCostumeRepository extends JpaRepository<EquippedCostume, EquippedCostumeId> {
	Optional<EquippedCostume> findByProfileMemberIdAndCostumeType(Long memberId, CostumeType type);
}
