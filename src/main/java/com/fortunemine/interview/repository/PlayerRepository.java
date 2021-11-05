package com.fortunemine.interview.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fortunemine.interview.model.entity.Player;

public interface PlayerRepository extends JpaRepository<Player, UUID> {
}
