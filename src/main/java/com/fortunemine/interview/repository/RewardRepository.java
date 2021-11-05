package com.fortunemine.interview.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fortunemine.interview.model.entity.Reward;

public interface RewardRepository extends JpaRepository<Reward, UUID> {
}
