package com.seemonkey.bananajump.item.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seemonkey.bananajump.item.domain.Inventory;
import com.seemonkey.bananajump.item.domain.InventoryId;

public interface InventoryRepository extends JpaRepository<Inventory, InventoryId> {
	List<Inventory> findById_MemberId(Long memberId);
	Optional<Inventory> findById_MemberIdAndId_ItemId(Long memberId, Long itemId);
}
