package com.fortunemine.interview.service;

import java.util.UUID;

import com.fortunemine.interview.payload.response.GeneralResponse;
import com.fortunemine.interview.payload.response.State;

public interface PlayerService {

    GeneralResponse levelUp(UUID playerID);
    GeneralResponse collectRewards(UUID playerID, UUID rewardID);
    State getPlayerState(UUID playerID);
}
