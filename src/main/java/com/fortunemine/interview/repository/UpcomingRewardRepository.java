package com.fortunemine.interview.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fortunemine.interview.model.entity.Player;
import com.fortunemine.interview.model.entity.Reward;
import com.fortunemine.interview.model.entity.UpcomingReward;

public interface UpcomingRewardRepository extends JpaRepository<UpcomingReward, UUID> {

    @Query(value = "SELECT r FROM UpcomingReward ur "
        + "JOIN ur.player p ON p.isDeleted = false "
        + "JOIN ur.reward r "
        + "WHERE  p = :player")
    List<Reward> findUpcomingRewardsByPlayer(@Param("player") Player player);

    @Query(value = "SELECT ur FROM UpcomingReward ur "
        + "JOIN ur.player p ON p.isDeleted = false "
        + "JOIN ur.reward r "
        + "WHERE  p.id = :playerId "
        + "AND r.id = :rewardId")
    UpcomingReward findUpcomingRewardByRewardAndPlayer(@Param("playerId") UUID playerId, @Param("rewardId") UUID rewardId);
}
