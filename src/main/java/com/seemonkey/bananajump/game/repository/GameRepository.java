package com.seemonkey.bananajump.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seemonkey.bananajump.game.domain.Record;

@Repository
public interface GameRepository extends JpaRepository<Record, Long> {
}
