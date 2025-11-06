package com.seemonkey.bananajump.costume.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seemonkey.bananajump.costume.domain.Costume;

public interface CostumeRepository extends JpaRepository<Costume, Long> {
}
