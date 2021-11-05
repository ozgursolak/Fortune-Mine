package com.fortunemine.interview.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fortunemine.interview.model.entity.Reward;
import com.fortunemine.interview.model.entity.LevelCompletionReward;

public interface LevelCompletionRewardRepository extends JpaRepository<LevelCompletionReward, UUID> {

    @Query(value = "SELECT r FROM LevelCompletionReward lcr "
        + "JOIN lcr.reward r "
        + "WHERE lcr.level = :level")
    List<Reward> findRewardsByLevel(int level);
}