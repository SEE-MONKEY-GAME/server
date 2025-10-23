package com.seemonkey.bananajump.item.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.seemonkey.bananajump.item.domain.Inventory;
import com.seemonkey.bananajump.item.domain.InventoryId;

import jakarta.transaction.Transactional;

public interface InventoryRepository extends JpaRepository<Inventory, InventoryId> {
	List<Inventory> findById_MemberId(Long memberId);
	Optional<Inventory> findById_MemberIdAndId_ItemId(Long memberId, Long itemId);

	@Modifying
	@Transactional
	@Query(value = """
        INSERT INTO inventory (member_id, item_id, quantity)
        VALUES (:memberId, :itemId, :addQty)
        ON DUPLICATE KEY UPDATE
          quantity = (quantity + VALUES(quantity))
        """, nativeQuery = true)
	int upsertInventory(
		@Param("memberId") Long memberId,
		@Param("itemId") Long itemId,
		@Param("addQty") int addQty
	);


	@Query(value = """
        SELECT quantity
        FROM inventory
        WHERE member_id = :memberId AND item_id = :itemId
        """, nativeQuery = true)
	Integer findQuantity(
		@Param("memberId") Long memberId,
		@Param("itemId") Long itemId
	);
}
