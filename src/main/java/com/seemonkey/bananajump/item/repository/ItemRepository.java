package com.seemonkey.bananajump.item.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seemonkey.bananajump.item.domain.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
	Optional<Item> findByCode(String itemCode);
}
