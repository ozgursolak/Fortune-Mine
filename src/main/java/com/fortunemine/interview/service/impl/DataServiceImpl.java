package com.fortunemine.interview.service.impl;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fortunemine.interview.model.entity.LevelCompletionReward;
import com.fortunemine.interview.model.entity.Player;
import com.fortunemine.interview.model.entity.Reward;
import com.fortunemine.interview.model.entity.Wallet;
import com.fortunemine.interview.model.entity.WalletReward;
import com.fortunemine.interview.repository.LevelCompletionRewardRepository;
import com.fortunemine.interview.repository.PlayerRepository;
import com.fortunemine.interview.repository.RewardRepository;
import com.fortunemine.interview.repository.WalletRepository;
import com.fortunemine.interview.repository.WalletRewardRepository;
import com.fortunemine.interview.service.DataService;

@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {

    private final RewardRepository rewardRepository;
    private final PlayerRepository playerRepository;
    private final WalletRepository walletRepository;
    private final WalletRewardRepository walletRewardRepository;
    private final LevelCompletionRewardRepository levelCompletionRewardRepository;

    @Override
    @Transactional
    public void initializeDB() {
        final Player player = createPlayer("Özgür Solak", "ozgur@gmail.com");
        final Wallet wallet = createWallet(player);
        final List<Reward> rewards = new ArrayList<>();
        final List<String> rewardNames = new ArrayList<>();

        populateRewardNames(rewardNames);

        for(int i = 0;i < rewardNames.size(); i++) {
            createReward(rewards, rewardNames.get(i), (i+1)*10);
        }

        for (int i = 0; i < 3; i++) {
            createWalletReward(wallet, rewards.get(i));
        }

        for(int i = 0; i< rewards.size(); i++) {
            createLevelCompletionReward(rewards.get(i), i);
        }

    }

    private void populateRewardNames(final List<String> rewardNames) {
        rewardNames.add("energy");
        rewardNames.add("coin");
        rewardNames.add("power");
        rewardNames.add("oil");
        rewardNames.add("length");
        rewardNames.add("time");
        rewardNames.add("strength");
    }

    private Wallet createWallet(final Player player) {
        final Wallet wallet = new Wallet();

        wallet.setPlayer(player);
        wallet.setLevel(0);

        walletRepository.save(wallet);

        return wallet;
    }

    private Player createPlayer(final String fullname, final String email) {
        final Player player = new Player();

        player.setFullName(fullname);
        player.setEmail(email);

        playerRepository.save(player);

        return player;
    }

    private void createWalletReward(final Wallet wallet, final Reward reward) {
        final WalletReward walletReward = new WalletReward();

        walletReward.setWallet(wallet);
        walletReward.setReward(reward);

        walletRewardRepository.save(walletReward);
    }

    private void createLevelCompletionReward(final Reward reward1, final int level) {
        final LevelCompletionReward levelCompletionReward1 = new LevelCompletionReward();

        levelCompletionReward1.setLevel(level);
        levelCompletionReward1.setReward(reward1);

        levelCompletionRewardRepository.save(levelCompletionReward1);
    }

    private void createReward(final List<Reward> rewards, final String name, final Integer amount) {
        final Reward reward = new Reward();

        reward.setName(name);
        reward.setAmount(amount);

        rewards.add(reward);

        rewardRepository.save(reward);
    }
}
