package com.fortunemine.interview.repository;

import java.util.UUID;

import com.fortunemine.interview.model.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {
}
