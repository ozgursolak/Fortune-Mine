package com.fortunemine.interview.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fortunemine.interview.model.entity.Reward;
import com.fortunemine.interview.model.entity.Wallet;
import com.fortunemine.interview.model.entity.WalletReward;

public interface WalletRewardRepository extends JpaRepository<WalletReward, UUID> {

    @Query(value = "SELECT r FROM WalletReward wr "
        + "JOIN wr.wallet w ON w.isDeleted = false "
        + "JOIN wr.reward r "
        + "WHERE  w = :wallet")
    List<Reward> findRewardsByWallet(@Param("wallet") Wallet wallet);
}
