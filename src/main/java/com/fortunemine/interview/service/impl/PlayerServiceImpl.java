package com.fortunemine.interview.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


import com.fortunemine.interview.constant.ErrorMessage;
import com.fortunemine.interview.exception.BaseException;
import com.fortunemine.interview.model.entity.Player;
import com.fortunemine.interview.model.entity.Reward;
import com.fortunemine.interview.model.entity.WalletReward;
import com.fortunemine.interview.model.entity.UpcomingReward;
import com.fortunemine.interview.model.entity.Wallet;
import com.fortunemine.interview.service.PlayerService;
import com.fortunemine.interview.payload.response.GeneralResponse;
import com.fortunemine.interview.payload.response.State;
import com.fortunemine.interview.repository.LevelCompletionRewardRepository;
import com.fortunemine.interview.repository.PlayerRepository;
import com.fortunemine.interview.repository.WalletRepository;
import com.fortunemine.interview.repository.WalletRewardRepository;
import com.fortunemine.interview.repository.UpcomingRewardRepository;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final WalletRepository walletRepository;
    private final LevelCompletionRewardRepository levelCompletionRewardRepository;
    private final WalletRewardRepository walletRewardRepository;
    private final UpcomingRewardRepository upcomingRewardRepository;

    @Override
    @Transactional
    public GeneralResponse levelUp(final UUID playerID) {
        final GeneralResponse response = new GeneralResponse();
        final Player player = getPlayer(playerID);

        try {
            final Wallet wallet = player.getWallet();
            final List<Reward> completedLevelRewards = levelCompletionRewardRepository
                .findRewardsByLevel(wallet.getLevel());

            createAndSaveUpcomingRewards(player, completedLevelRewards);
            increasePlayerLevel(wallet);

            final State state = getPlayerState(playerID);

            response.setIsSuccess(Boolean.TRUE);
            response.setState(state);
        } catch (RuntimeException e) {
            throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessage.LEVEL_NOT_INCREASED);
        }

        return response;
    }

    @Override
    @Transactional
    public GeneralResponse collectRewards(final UUID playerID, final UUID rewardID) {
        final GeneralResponse response = new GeneralResponse();

        try {
            final Player player = getPlayer(playerID);
            final Reward reward = getPlayerUpcomingReward(playerID, rewardID);

            saveWalletReward(player.getWallet(), reward);

            response.setState(getPlayerState(playerID));
            response.setIsSuccess(true);
        } catch (RuntimeException e) {
            throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessage.REWARD_NOT_COLLECTED);
        }

        return response;
    }

    @Override
    public State getPlayerState(final UUID playerID) {
        final Player player = getPlayer(playerID);
        final Wallet wallet = player.getWallet();
        final List<Reward> playerCurrentRewards = walletRewardRepository.findRewardsByWallet(wallet);
        final List<Reward> upcomingRewards = upcomingRewardRepository.findUpcomingRewardsByPlayer(player);
        final Map<String,Integer> walletInfoMap = createWalletInfoMap(wallet, playerCurrentRewards);
        final List<Map<String, Object>> rewardInfoMaps = createRewardInfoMap(upcomingRewards);

        final State state = new State(
            player,
            walletInfoMap,
            rewardInfoMaps
        );

        return state;
    }

    private List<Map<String, Object>> createRewardInfoMap(final List<Reward> upcomingRewards) {
        if (CollectionUtils.isEmpty(upcomingRewards)) {
            return null;
        }

        final List<Map<String, Object>> rewardInfoMaps = new ArrayList<>();

        upcomingRewards.forEach(upcomingReward -> {
            final Map<String, Object> rewardInfoMap = createRewardInfoMap(upcomingReward);

            rewardInfoMaps.add(rewardInfoMap);
        });

        return rewardInfoMaps;
    }

    private Map<String, Integer> createWalletInfoMap(final Wallet wallet, final List<Reward> playerCurrentRewards) {
        if (CollectionUtils.isEmpty(playerCurrentRewards)) {
            return null;
        }

        final Map<String, Integer> walletInfoMap = new HashMap<>();
        final Map<String, Integer> rewardNameRewardAmountMap =  playerCurrentRewards
            .stream()
            .collect(Collectors.groupingBy(Reward::getName, Collectors.summingInt(Reward::getAmount)));

        walletInfoMap.put("level", wallet.getLevel());
        walletInfoMap.putAll(rewardNameRewardAmountMap);

        return walletInfoMap;
    }

    private Map<String, Object> createRewardInfoMap(final Reward upcomingReward) {
        final Map<String, Object> rewardInfoMap = new LinkedHashMap<>();
        final Map<String, Integer> rewardNameRewardAmountMap = new HashMap<>();

        rewardNameRewardAmountMap.put(upcomingReward.getName(), upcomingReward.getAmount());

        rewardInfoMap.put("reward_id", upcomingReward.getId());
        rewardInfoMap.put("reward", rewardNameRewardAmountMap);

        return rewardInfoMap;
    }

    private void saveWalletReward(final Wallet wallet, final Reward completedLevelReward) {
        final WalletReward walletReward = new WalletReward();

        walletReward.setReward(completedLevelReward);
        walletReward.setWallet(wallet);

        walletRewardRepository.save(walletReward);
    }

    private Player getPlayer(final UUID playerID) {
        return playerRepository.findById(playerID)
            .orElseThrow(() ->
                new BaseException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    String.format(ErrorMessage.USER_NOT_FOUND, playerID)));
    }

    private Reward getPlayerUpcomingReward(final UUID playerID, final UUID rewardID) {
        final UpcomingReward upcomingReward = upcomingRewardRepository.findUpcomingRewardByRewardAndPlayer(
            playerID,
            rewardID
        );

        if (Objects.isNull(upcomingReward)) {
            throw new BaseException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                String.format(ErrorMessage.REWARD_NOT_FOUND, rewardID));
        }

        upcomingReward.setIsDeleted(Boolean.TRUE);

        upcomingRewardRepository.save(upcomingReward);

        return upcomingReward.getReward();
    }

    private void createAndSaveUpcomingRewards(final Player player, final List<Reward> completedLevelRewards) {
        if (CollectionUtils.isEmpty(completedLevelRewards)) {
            return;
        }

        for (final Reward levelCompletedReward: completedLevelRewards) {
            final UpcomingReward upcomingReward = new UpcomingReward();

            upcomingReward.setReward(levelCompletedReward);
            upcomingReward.setPlayer(player);

            upcomingRewardRepository.save(upcomingReward);
        }
    }

    private void increasePlayerLevel(final Wallet wallet) {
        final int currentLevel = wallet.getLevel();

        wallet.setLevel(currentLevel + 1);

        walletRepository.save(wallet);
    }
}
