package com.fortunemine.interview.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Endpoint {

    public final String PLAYER_LEVEL_UP = "/{playerID}/levelup";
    public final String COLLECT_REWARD = "/{playerID}/collect/{rewardID}";
    public final String GET_PLAYER_STATE = "/{playerID}/state";
    public final String INITIALIZE_DB = "/initialize";
}
